package com.sandeep.async.services;

import com.sandeep.async.models.Employee;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class EmployeeService {

    private static final List<Employee> techEmployees = Arrays.asList(
            new Employee("Sandeep", "Tech"),
            new Employee("Mark", "Tech"),
            new Employee("Suresh", "Tech")
    );

    private static final List<Employee> businessEmployees = Arrays.asList(
            new Employee("Rajeev", "Business"),
            new Employee("Andrew", "Business"),
            new Employee("Dinesh", "Business")
    );

    public List<Employee> getTechEmployees() throws InterruptedException {
        Thread.sleep(3000);
        return techEmployees;
    }

    public List<Employee> getBusinessEmployees() throws InterruptedException {
        Thread.sleep(4000);
        return businessEmployees;
    }

    @Async
    public CompletableFuture<List<Employee>> getTechEmployeesAsync()
            throws InterruptedException {
        Thread.sleep(3000);
        return CompletableFuture.completedFuture(techEmployees);
    }

    @Async
    public CompletableFuture<List<Employee>> getBusinessEmployeesAsync()
            throws InterruptedException {
        Thread.sleep(4000);
        return CompletableFuture.completedFuture(businessEmployees);
    }

}
