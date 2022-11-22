package com.rntgroup.advanced.multithreading.task.fourth;

import java.util.List;

public class HiredEmployeesMain {

    public static void main(String[] args) {
        Facade facade = new Facade();

        List<Employee> hiredEmployees = facade.getHiredEmployees();
        printResult(hiredEmployees);

        System.out.println("Было создано " + Database.getHiredEmployees().size() + " штатных сотрудников");
        System.out.println("Метод вернул " + hiredEmployees.size() + " штатных сотрудников с их зарплатами");
    }

    public static void printResult(List<Employee> employees) {
        employees.forEach(System.out::println);
    }
}
