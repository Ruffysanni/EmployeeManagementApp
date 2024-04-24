package com.ruffy.EmployeeManagementApp.service;


import com.ruffy.EmployeeManagementApp.model.Employee;
import com.ruffy.EmployeeManagementApp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;


    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    public Employee addNewEmployee(Employee employee){
        return employeeRepository.save(employee);
    }
}
