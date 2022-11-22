package com.rntgroup.advanced.multithreading.task.fourth;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Database {

    private Database() {

    }

    private static final List<Employee> employees = new ArrayList<>();
    private static final List<Salary> salaries = new ArrayList<>();
    private static final Random random = new Random();

    static {
        String[] names = {
                "Джон", "Майкл", "Сара", "Ибрагим", "Мария"
        };

        for (int i = 0; i < 100000; i++) {
            Employee employee = new Employee()
                    .setId((long) (i + 1))
                    .setName(names[random.nextInt(names.length)])
                    .setHired(Math.random() > 0.5);

            employees.add(employee);
        }

        employees.stream()
                .map(Employee::getId)
                .forEach(id -> {
                    Salary salary = new Salary()
                            .setEmployeeId(id)
                            .setValue(random.nextLong(10000));

                    salaries.add(salary);
                });

        System.out.println("База данных загружена");
    }

    public static List<Employee> getHiredEmployees() {
        return employees.stream().filter(Employee::isHired).toList();
    }

    public static Salary getSalaryByEmployeeId(Long employeeId) {
        return salaries.stream()
                .filter(salary -> salary.getEmployeeId().equals(employeeId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Employee not found!"));
    }
}
