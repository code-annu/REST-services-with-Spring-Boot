package com.example.rest_services.controllers;

import com.example.rest_services.exceptions.EmployeeNotFoundException;
import com.example.rest_services.models.Employee;
import com.example.rest_services.repositories.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeRepo employeeRepo;

    @GetMapping("/")
    String home() {
        return "This is home page nothing is here.\n Visit '/employees', '/'";
    }


    @GetMapping("/employees")
    CollectionModel<EntityModel<Employee>> allEmployees() {
        List<EntityModel<Employee>> employees = this.employeeRepo.findAll().stream().map(employee -> EntityModel.of(employee,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).findEmployee(employee.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).allEmployees()).withRel("employees")
        )).toList();

        return CollectionModel.of(employees,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).allEmployees()).withSelfRel());
    }

    @PostMapping("/employees")
    Employee addEmployee(@RequestBody Employee employee) {
        return this.employeeRepo.save(employee);
    }

    @GetMapping("/employees/{id}")
    EntityModel<Employee> findEmployee(@PathVariable(value = "id", required = true) Long id) {
        Employee employee = this.employeeRepo.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        return EntityModel.of(employee,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).findEmployee(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).allEmployees()).withRel("employees")
        );
    }

    @PutMapping("/employees/{id}")
    Employee replaceEmployee(@PathVariable(value = "id", required = true) Long id, @RequestBody Employee newEmployee) {
        return this.employeeRepo.findById(id).map(employee -> {
            employee.setRole(newEmployee.getRole());
            employee.setName(newEmployee.getName());
            return this.employeeRepo.save(employee);
        }).orElseGet(() -> this.employeeRepo.save(newEmployee));
    }

    @DeleteMapping("/employees/{id}")
    void deleteEmployee(@PathVariable(value = "id", required = true) Long id) {
        this.employeeRepo.deleteById(id);

    }
}
