/*
 * Copyright Axemtum 2024
 */
package com.axemtum.manager.database;

import com.axemtum.manager.database.object.Assignment;
import com.axemtum.manager.database.object.Employee;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * The employee database loader, that will create the database and initialize
 * every {@link Employee} that is not already existing in the database.
 *
 * @author Magnus Rossander
 */
@Component
public class EmployeeDatabaseLoader implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;

    private static final String MANAGER_HARIS = "Haris Kapidzic";
    private static final String MANAGER_HANNA = "Hanna Åstrand";
    private static final String MANAGER_EMMA = "Emma Alikadic";

    @Autowired
    public EmployeeDatabaseLoader(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        initDatabase();
    }

    private void initDatabase() throws Exception {
        List<String> storedEmployees = getStoredEmployees();
        for (Employee employeeToInitialize : getEmployeesToInitialize()) {
            if (!storedEmployees.contains(employeeToInitialize.getEmail())) {
                this.employeeRepository.save(employeeToInitialize);
            }
        }
    }

    private List<String> getStoredEmployees() {
        Iterable<Employee> storedDataEmployees = employeeRepository.findAll();
        List<String> storedEmployees = new ArrayList<>();
        for (Employee storedDataEmployee : storedDataEmployees) {
            storedEmployees.add(storedDataEmployee.getEmail());
        }
        return storedEmployees;
    }

    private List<Employee> getEmployeesToInitialize() {
        List<Employee> employeesToInitialize = new ArrayList<>();

        employeesToInitialize.add(new Employee("magnus.rossander@axemtum.com", "Magnus", "Rossander", 1, MANAGER_HARIS, new Assignment("Vector", "2022-09-01T00:00:00", "2023-12-11T00:00:00", "System developer")));
        employeesToInitialize.add(new Employee("lilian.zakrisson@axemtum.com", "Lilian", "Zakrisson", 2, MANAGER_HARIS, new Assignment("Saab", "2022-09-01T00:00:00", "2025-09-01T00:00:00", "Project engineer")));
        employeesToInitialize.add(new Employee("niklas.lundvall@axemtum.com", "Niklas", "Lundvall", 3, MANAGER_HARIS, new Assignment("Volvo", "2022-09-01T00:00:00", "2025-09-01T00:00:00", "System developer")));
        employeesToInitialize.add(new Employee("christopher.tvede-moller@axemtum.com", "Christopher", "Tvede-Möller", 4, MANAGER_HARIS, new Assignment("Nibe", "2023-02-01T00:00:00", "2025-02-01T00:00:00", "System developer")));

        employeesToInitialize.add(new Employee("andreas.rydell@axemtum.com", "Andreas", "Rydell", 5, MANAGER_HANNA, new Assignment("Saab", "2023-10-01T00:00:00", "2025-10-01T00:00:00", "System developer")));
        employeesToInitialize.add(new Employee("olof.stridsberg@axemtum.com", "Olof", "Stridsberg", 6, MANAGER_HANNA, new Assignment("Nibe", "2023-10-01T00:00:00", "2025-10-01T00:00:00", "System developer")));

        employeesToInitialize.add(new Employee("sebastian.nyberg@axemtum.com", "Sebastian", "Nyberg", 7, MANAGER_EMMA, new Assignment("Unknown", "2022-09-01T00:00:00", "2022-10-01T00:00:00", "Unknown")));

        return employeesToInitialize;
    }

}
