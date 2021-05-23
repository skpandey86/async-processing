package com.sandeep.async.controllers;

import com.sandeep.async.models.Employee;
import com.sandeep.async.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@RestController
public class EmployeeController {

    private static final String SYNC_PROCESSING_MODE = "sync";
    private static final String ASYNC_PROCESSING_MODE = "async";

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/demo/employees")
    public List<Employee> getEmployees(@RequestParam String mode){
        List<Employee> employees = new ArrayList<>();

        if (SYNC_PROCESSING_MODE.equals(mode)) {

            try {
                employees.addAll(employeeService.getTechEmployees());
                employees.addAll(employeeService.getBusinessEmployees());
            } catch (InterruptedException ex){
                System.out.println("Exception in calling employee service");
            }

        } else if (ASYNC_PROCESSING_MODE.equals(mode)) {

            try {
                CompletableFuture<List<Employee>> techEmployeesFuture = employeeService.getTechEmployeesAsync();
                CompletableFuture<List<Employee>> businessEmployeesFuture = employeeService.getBusinessEmployeesAsync();
                CompletableFuture.allOf(techEmployeesFuture, businessEmployeesFuture).join();
                employees.addAll(techEmployeesFuture.get());
                employees.addAll(techEmployeesFuture.get());

            } catch (InterruptedException ex){
                System.out.println("Exception in calling employee service");
            } catch (ExecutionException ex){
                System.out.println("Exception getting the employee list from CompletableFuture");
            }
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "processing mode couldn't be found");
        }

        return employees;
    }
}
