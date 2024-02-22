/*
 * Copyright Axemtum 2024
 */
package com.axemtum.manager.database;

import com.axemtum.manager.database.object.Employee;
import org.springframework.data.repository.CrudRepository;

/**
 * The employee repository
 *
 * @author Magnus Rossander
 */
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

}
