package com.ruffy.EmployeeManagementApp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity(name = "employees_table")
public class Employee {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.UUID)
    private Integer employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String department;
    private String company;
    
}
