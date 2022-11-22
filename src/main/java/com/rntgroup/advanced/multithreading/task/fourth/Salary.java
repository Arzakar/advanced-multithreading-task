package com.rntgroup.advanced.multithreading.task.fourth;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Salary {

    Long employeeId;
    long value;

}
