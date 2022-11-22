package com.rntgroup.advanced.multithreading.task.fourth;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employee {

    Long id;
    String name;
    boolean hired;
    Salary salary;

    public String toString() {
        return """
               Имя: %s
                    ID: %s
                    В штате: %s
                    Зарплата: %d
               """.formatted(name, id.toString(), hired ? "Да" : "Нет", salary.getValue());
    }
}
