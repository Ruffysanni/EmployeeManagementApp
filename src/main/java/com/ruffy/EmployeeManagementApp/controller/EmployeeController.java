package com.ruffy.EmployeeManagementApp.controller;


import com.ruffy.EmployeeManagementApp.model.Employee;
import com.ruffy.EmployeeManagementApp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;


    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    @PostMapping ("/employee")
    public Employee addEmployee(@RequestBody Employee employee){
        return employeeService.addNewEmployee(employee);
    }

}
