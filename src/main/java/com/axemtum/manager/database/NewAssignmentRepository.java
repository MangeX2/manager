/*
 * Copyright Axemtum 2024
 */
package com.axemtum.manager.database;

import com.axemtum.manager.database.object.NewAssignment;
import org.springframework.data.repository.CrudRepository;

/**
 * The new assignment repository
 *
 * @author Magnus Rossander
 */
public interface NewAssignmentRepository extends CrudRepository<NewAssignment, Long> {

}
