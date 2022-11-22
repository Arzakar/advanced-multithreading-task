package com.rntgroup.advanced.multithreading.task.factorial;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FactorialCalculatorTask extends RecursiveTask<BigInteger> {

    int[] array;

    @Override
    protected BigInteger compute() {
        if (array.length == 1) {
            return BigInteger.valueOf(array[0]);
        }

        if (array.length == 2) {
            return BigInteger.valueOf((long) array[0] * array[1]);
        }

        var leftHalf = new FactorialCalculatorTask(Arrays.copyOfRange(array, 0, array.length / 2));
        var rightHalf = new FactorialCalculatorTask(Arrays.copyOfRange(array, array.length / 2, array.length));

        leftHalf.fork();
        rightHalf.fork();

        return leftHalf.join().multiply(rightHalf.join());
    }
}
