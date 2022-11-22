package com.rntgroup.advanced.multithreading.task.fourth;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Facade {

    public List<Employee> getHiredEmployees() {
        return hiredEmployees()
                .thenApplyAsync(employees -> employees.parallelStream()
                        .map(this::applySalary)
                        .toList())
                .thenApplyAsync(this::extractEmployees)
                .join();
    }

    private CompletableFuture<List<Employee>> hiredEmployees() {
        return CompletableFuture.supplyAsync(Database::getHiredEmployees);
    }

    private CompletableFuture<Employee> applySalary(Employee employee) {
        return CompletableFuture.supplyAsync(() -> employee.setSalary(Database.getSalaryByEmployeeId(employee.getId())));
    }

    private List<Employee> extractEmployees(List<CompletableFuture<Employee>> futures) {
        return futures.stream()
                .map(CompletableFuture::join)
                .toList();
    }
}
