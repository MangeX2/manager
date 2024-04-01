/* 
 * Copyright Axemtum 2024
 */
import React, {Component} from 'react';
import {DayPilot, DayPilotScheduler} from "daypilot-pro-react";
import DraggableItem from "./draggableItem";
const when = require('when');
const client = require('./../client');
const follow = require('./../follow');
const stompClient = require('./../websocket-listener');
const root = '/api';
class ManagerChart extends Component {

    constructor(props) {
        super(props);
        this.schedulerRef = React.createRef();
        const messageInfo = "INFO: ";
        this.onCreate = this.onCreate.bind(this);
        this.onUpdate = this.onUpdate.bind(this);
        this.onDelete = this.onDelete.bind(this);
        this.refreshCurrentPage = this.refreshCurrentPage.bind(this);
        this.state = {
            startDate: "2022-09-01",
            scale: "Month",
            days: 1440,
            timeHeaders: [{groupBy: "Year", format: "yyyy"}, {groupBy: "Month", format: "M"}],
            cellWidthSpec: "Auto",
            onEventMoved: function (args) {
                this.message(messageInfo + args.e.text() + " was moved!");
            },
            onEventResized: function (args) {
                this.message(messageInfo + args.e.text() + " was resized! New start: " + args.newStart + " and end: " + args.newEnd);
            },
            treeEnabled: true,
            durationBarVisible: false,
            employees: [],
            links: {},
            loggedInManager: this.props.loggedInManager,
            assignments: []
        };
    }

    getName(employee) {
        return employee.firstName + " " + employee.lastName;
    }

    getId(employee) {
        return employee.firstName + employee.lastName;
    }

    getInfo(employee) {
        return employee.assignment.role + " at <b>" + employee.assignment.company + "</b>";
    }

    loadFromServer() {
        follow(client, root, [{rel: 'employees'}]
                ).then(employeeCollection => {
            return client({method: 'GET', path: employeeCollection.entity._links.profile.href, headers: {'Accept': 'application/schema+json'}}
            ).then(schema => {
                Object.keys(schema.entity.properties).forEach(function (property) {
                    if (schema.entity.properties[property].hasOwnProperty('format') &&
                            schema.entity.properties[property].format === 'uri') {
                        delete schema.entity.properties[property];
                    } else if (schema.entity.properties[property].hasOwnProperty('$ref')) {
                        delete schema.entity.properties[property];
                    }
                });
                this.schema = schema.entity;
                this.links = employeeCollection.entity._links;
                return employeeCollection;
            });
        }).then(employeeCollection => {
            return employeeCollection.entity._embedded.employees.map(employee =>
                client({method: 'GET', path: employee._links.self.href})
            );
        }).then(employeePromises => {
            return when.all(employeePromises);
        }).done(employees => {
            console.log('Load from server!');
            const haris = "Haris";
            const harisEmployees = employees.filter(employee => employee.entity.manager.includes(haris));
            const hanna = "Hanna";
            const hannaEmployees = employees.filter(employee => employee.entity.manager.includes(hanna));
            const emma = "Emma";
            const emmaEmployees = employees.filter(employee => employee.entity.manager.includes(emma));
            const employeeEvents = employees.filter(employee => employee.entity.assignment.company.includes("Unknown") === false);
            this.setState({
                employees: harisEmployees + hannaEmployees + emmaEmployees,
                links: this.links,
                resources: [
                    {name: haris, expanded: true, children:
                                harisEmployees.map(harisEmployee => (
                                            {name: this.getName(harisEmployee.entity), id: this.getId(harisEmployee.entity)}
                                    ))},
                    {name: hanna, expanded: true, children:
                                hannaEmployees.map(hannaEmployee => (
                                            {name: this.getName(hannaEmployee.entity), id: this.getId(hannaEmployee.entity)}
                                    ))},
                    {name: emma, expanded: true, children:
                                emmaEmployees.map(emmaEmployee => (
                                            {name: this.getName(emmaEmployee.entity), id: this.getId(emmaEmployee.entity)}
                                    ))}
                ],
                events: employeeEvents.map(employeeEvent => (
                            {
                                id: employeeEvent.entity.chartId,
                                text: employeeEvent.entity.assignment.company,
                                start: employeeEvent.entity.assignment.startDate,
                                end: employeeEvent.entity.assignment.endDate,
                                resource: this.getId(employeeEvent.entity),
                                bubbleHtml: this.getInfo(employeeEvent.entity)
                            }
                    ))
            });
        });
    }

    onCreate(newEmployee) {
        follow(client, root, ['employees']).done(response => {
            client({method: 'POST', path: response.entity._links.self.href, entity: newEmployee, headers: {'Content-Type': 'application/json'}
            });
        });
    }
    onUpdate(employee, updatedEmployee) {
        if (employee.entity.manager.name === this.state.loggedInManager) {
            updatedEmployee["manager"] = employee.entity.manager;
            client({
                method: 'PUT',
                path: employee.entity._links.self.href,
                entity: updatedEmployee,
                headers: {
                    'Content-Type': 'application/json',
                    'If-Match': employee.headers.Etag
                }
            }).done(response => {
                /* Let the websocket handler update the state */
            }, response => {
                if (response.status.code === 403) {
                    alert('ACCESS DENIED: You are not authorized to update ' +
                            employee.entity._links.self.href);
                }
                if (response.status.code === 412) {
                    alert('DENIED: Unable to update ' + employee.entity._links.self.href +
                            '. Your copy is stale.');
                }
            });
        } else {
            alert("You are not authorized to update");
        }
    }

    onDelete(employee) {
        client({method: 'DELETE', path: employee.entity._links.self.href}
        ).done(response => {/* let the websocket handle updating the UI */
        },
                response => {
                    if (response.status.code === 403) {
                        alert('ACCESS DENIED: You are not authorized to delete ' +
                                employee.entity._links.self.href);
                    }
                });
    }

    refreshCurrentPage(message) {
        follow(client, root, [{
                rel: 'employees',
                params: {
                    size: this.state.pageSize,
                    page: this.state.page.number
                }
            }]).then(employeeCollection => {
            this.links = employeeCollection.entity._links;
            this.page = employeeCollection.entity.page;
            return employeeCollection.entity._embedded.employees.map(employee => {
                return client({
                    method: 'GET',
                    path: employee._links.self.href
                });
            });
        }).then(employeePromises => {
            return when.all(employeePromises);
        }).then(employees => {
            this.setState({
                page: this.page,
                employees: employees,
                attributes: Object.keys(this.schema.properties),
                pageSize: this.state.pageSize,
                links: this.links
            });
        });
    }

    componentDidMount() {
        this.loadFromServer();
        stompClient.register([
            {route: '/topic/newEmployee', callback: this.refreshCurrentPage},
            {route: '/topic/updateEmployee', callback: this.refreshCurrentPage},
            {route: '/topic/deleteEmployee', callback: this.refreshCurrentPage}
        ]);
    }

    render() {
        return (
                <div style={{display: "flex", marginBottom: "30px"}}>
                    <AvailableJobs/>
                    <div style={{flex: 1}}>
                        <DayPilotScheduler
                            {...this.state}
                            ref={this.schedulerRef}
                            />
                    </div>
                </div>
                );
    }
}

class AvailableJobs extends React.Component {

    render() {
        return (
                <div className={"draggable-container"}>
                    <div className={"draggable-header"}>Available jobs</div>
                    {
                        ["CompanyA", "CompanyB", "CompanyC", "CompanyD", "CompanyE"].map((name, index) => {
                            return <DraggableItem key={101 + index} id={101 + index} company={name} days={360}></DraggableItem>;
                        })
                    }
                </div>
                );
    }
}

export default ManagerChart;