/*
 * Copyright Axemtum 2024
 */
package com.axemtum.manager.database;

import com.axemtum.manager.database.object.Assignment;
import com.axemtum.manager.database.object.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * The database loader
 *
 * @author Magnus Rossander
 */
@Component
public class DatabaseLoader implements CommandLineRunner {

    private final EmployeeRepository repository;

    @Autowired
    public DatabaseLoader(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... strings) throws Exception {
        this.repository.save(new Employee("Magnus", "Rossander", 1, "Haris", new Assignment("Vector", "2022-09-01T00:00:00", "2023-12-11T00:00:00", "System developer")));
        this.repository.save(new Employee("Lilian", "Zakrisson", 2, "Haris", new Assignment("Saab", "2022-09-01T00:00:00", "2025-09-01T00:00:00", "Project engineer")));
        this.repository.save(new Employee("Niklas", "Lundvall", 3, "Haris", new Assignment("Volvo", "2022-09-01T00:00:00", "2025-09-01T00:00:00", "System developer")));
        this.repository.save(new Employee("Christopher", "Tvede-Möller", 4, "Haris", new Assignment("Nibe", "2023-02-01T00:00:00", "2025-02-01T00:00:00", "System developer")));

        this.repository.save(new Employee("Andreas", "Rydell", 5, "Hanna", new Assignment("Saab", "2023-10-01T00:00:00", "2025-10-01T00:00:00", "System developer")));
        this.repository.save(new Employee("Olof", "Stridsberg", 6, "Hanna", new Assignment("Nibe", "2023-10-01T00:00:00", "2025-10-01T00:00:00", "System developer")));

        this.repository.save(new Employee("Sebastian", "Nyberg", 7, "Emma", new Assignment("Unknown", "2022-09-01T00:00:00", "2022-10-01T00:00:00", "Unknown")));
    }

}
