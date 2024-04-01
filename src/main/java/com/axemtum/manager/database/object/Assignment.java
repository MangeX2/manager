/*
 * Copyright Axemtum 2024
 */
package com.axemtum.manager.database.object;

import jakarta.persistence.Embeddable;

/**
 * The assignment object
 *
 * @author Magnus Rossander
 */
@Embeddable
public class Assignment {

    private String company;
    private String startDate;
    private String endDate;
    private String role;

    @SuppressWarnings("unused")
    private Assignment() {
        // Used by database
    }

    public Assignment(String company, String startDate, String endDate, String role) {
        this.company = company;
        this.startDate = startDate;
        this.endDate = endDate;
        this.role = role;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
