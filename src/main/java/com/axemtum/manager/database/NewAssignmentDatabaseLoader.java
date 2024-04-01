/*
 * Copyright Axemtum 2024
 */
package com.axemtum.manager.database;

import com.axemtum.manager.database.object.NewAssignment;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * The new assignment database loader, that will create the database and
 * initialize every {@link NewAssignment} that is not already existing in the
 * database.
 *
 * @author Magnus Rossander
 */
@Component
public class NewAssignmentDatabaseLoader implements CommandLineRunner {

    private final NewAssignmentRepository newAssignmentRepository;

    @Autowired
    public NewAssignmentDatabaseLoader(NewAssignmentRepository newAssignmentRepository) {
        this.newAssignmentRepository = newAssignmentRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        initDatabase();
    }

    private void initDatabase() throws Exception {
        List<String> storedNewAssignments = getStoredNewAssignments();
        for (NewAssignment newAssignmentToInitialize : getNewAssignmentsToInitialize()) {
            if (!storedNewAssignments.contains(newAssignmentToInitialize.getCompany())) {
                this.newAssignmentRepository.save(newAssignmentToInitialize);
            }
        }
    }

    private List<String> getStoredNewAssignments() {
        Iterable<NewAssignment> storedDataNewAssignments = newAssignmentRepository.findAll();
        List<String> storedNewAssignments = new ArrayList<>();
        for (NewAssignment storedDataNewAssignment : storedDataNewAssignments) {
            storedNewAssignments.add(storedDataNewAssignment.getCompany());
        }
        return storedNewAssignments;
    }

    private List<NewAssignment> getNewAssignmentsToInitialize() {
        List<NewAssignment> newAssignmentsToInitialize = new ArrayList<>();

        newAssignmentsToInitialize.add(new NewAssignment("Dice"));
        newAssignmentsToInitialize.add(new NewAssignment("Massive Entertainment"));
        newAssignmentsToInitialize.add(new NewAssignment("Disney"));
        newAssignmentsToInitialize.add(new NewAssignment("Coca Cola"));
        newAssignmentsToInitialize.add(new NewAssignment("Microsoft"));

        return newAssignmentsToInitialize;
    }

}
