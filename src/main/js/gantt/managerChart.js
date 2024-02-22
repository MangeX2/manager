/* 
 * Copyright Axemtum 2024
 */
import React, {Component} from 'react';
import {DayPilot, DayPilotScheduler} from "daypilot-pro-react";
import DraggableItem from "./draggableItem";
const client = require('./../client');

class ManagerChart extends Component {

    constructor(props) {
        super(props);

        this.schedulerRef = React.createRef();
        const messageInfo = "INFO: ";

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
            employees: []
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
        client({method: 'GET', path: '/api/employees'}).done(response => {
            const haris = "Haris";
            const harisEmployees = response.entity._embedded.employees.filter(employee => employee.manager.includes(haris));
            const hanna = "Hanna";
            const hannaEmployees = response.entity._embedded.employees.filter(employee => employee.manager.includes(hanna));
            const emma = "Emma";
            const emmaEmployees = response.entity._embedded.employees.filter(employee => employee.manager.includes(emma));
            const employeeEvents = response.entity._embedded.employees.filter(employee => employee.assignment.company.includes("Unknown") === false);
            this.setState({
                employees: harisEmployees + hannaEmployees + emmaEmployees,
                resources: [
                    {name: haris, expanded: true, children:
                                harisEmployees.map(harisEmployee => (
                                            {name: this.getName(harisEmployee), id: this.getId(harisEmployee)}
                                    ))},
                    {name: hanna, expanded: true, children:
                                hannaEmployees.map(hannaEmployee => (
                                            {name: this.getName(hannaEmployee), id: this.getId(hannaEmployee)}
                                    ))},
                    {name: emma, expanded: true, children:
                                emmaEmployees.map(emmaEmployee => (
                                            {name: this.getName(emmaEmployee), id: this.getId(emmaEmployee)}
                                    ))}
                ],
                events: employeeEvents.map(employeeEvent => (
                            {
                                id: employeeEvent.chartId,
                                text: employeeEvent.assignment.company,
                                start: employeeEvent.assignment.startDate,
                                end: employeeEvent.assignment.endDate,
                                resource: this.getId(employeeEvent),
                                bubbleHtml: this.getInfo(employeeEvent)
                            }
                    ))
            });
        });
    }

    componentDidMount() {
        this.loadFromServer();
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
                    <DraggableItem id={101} company={"CompanyA"} days={360}></DraggableItem>
                    <DraggableItem id={102} company={"CompanyB"} days={360}></DraggableItem>
                    <DraggableItem id={103} company={"CompanyC"} days={360}></DraggableItem>
                    <DraggableItem id={104} company={"CompanyD"} days={360}></DraggableItem>
                    <DraggableItem id={105} company={"CompanyE"} days={360}></DraggableItem>
                </div>
                );
    }
}

export default ManagerChart;