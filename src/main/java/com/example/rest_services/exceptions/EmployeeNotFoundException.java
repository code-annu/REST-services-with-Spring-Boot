package com.example.rest_services.exceptions;

public class EmployeeNotFoundException extends RuntimeException{
    public EmployeeNotFoundException(Long id){
        super("Could not find employee "+id);
    }
}
