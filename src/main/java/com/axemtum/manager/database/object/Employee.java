/*
 * Copyright Axemtum 2024
 */
package com.axemtum.manager.database.object;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.Objects;

/**
 * The employee object
 *
 * @author Magnus Rossander
 */
@Entity
public class Employee {

    private @Id
    @GeneratedValue
    Long id;
    private String email;
    private String firstName;
    private String lastName;
    private int chartId;
    private String manager;
    @Embedded
    private Assignment assignment;

    @SuppressWarnings("unused")
    private Employee() {
        // Used by database
    }

    public Employee(String email, String firstName, String lastName, int chartId, String manager, Assignment assignment) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.chartId = chartId;
        this.manager = manager;
        this.assignment = assignment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id)
                && Objects.equals(email, employee.email)
                && Objects.equals(firstName, employee.firstName)
                && Objects.equals(lastName, employee.lastName)
                && Objects.equals(chartId, employee.chartId)
                && Objects.equals(manager, employee.manager)
                && Objects.equals(assignment.getCompany(), employee.assignment.getCompany())
                && Objects.equals(assignment.getStartDate(), employee.assignment.getStartDate())
                && Objects.equals(assignment.getEndDate(), employee.assignment.getEndDate())
                && Objects.equals(assignment.getRole(), employee.assignment.getRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, firstName, lastName, chartId, manager, assignment.getCompany(), assignment.getStartDate(), assignment.getEndDate(), assignment.getRole());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getChartId() {
        return chartId;
    }

    public void setChartId(int chartId) {
        this.chartId = chartId;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    @Override
    public String toString() {
        return "Employee{"
                + "id=" + id
                + ", email='" + email + '\''
                + ", firstName='" + firstName + '\''
                + ", lastName='" + lastName + '\''
                + ", chartId='" + chartId + '\''
                + ", manager='" + manager + '\''
                + ", assignment.company='" + assignment.getCompany() + '\''
                + ", assignment.startDate='" + assignment.getStartDate() + '\''
                + ", assignment.etartDate='" + assignment.getEndDate() + '\''
                + ", assignment.role='" + assignment.getRole() + '\''
                + '}';
    }

}
