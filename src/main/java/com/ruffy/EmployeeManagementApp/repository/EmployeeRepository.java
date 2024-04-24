package com.ruffy.EmployeeManagementApp.repository;

import com.ruffy.EmployeeManagementApp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
