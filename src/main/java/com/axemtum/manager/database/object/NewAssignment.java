/*
 * Copyright Axemtum 2024
 */
package com.axemtum.manager.database.object;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.Objects;

/**
 * The new assignment object
 *
 * @author Magnus Rossander
 */
@Entity
public class NewAssignment {

    private @Id
    @GeneratedValue
    Long id;
    private String company;

    @SuppressWarnings("unused")
    private NewAssignment() {
        // Used by database
    }

    public NewAssignment(String company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NewAssignment newAssignment = (NewAssignment) o;
        return Objects.equals(id, newAssignment.id)
                && Objects.equals(company, newAssignment.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, company);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

}
