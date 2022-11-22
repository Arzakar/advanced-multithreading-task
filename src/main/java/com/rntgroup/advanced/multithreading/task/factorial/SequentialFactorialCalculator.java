package com.rntgroup.advanced.multithreading.task.factorial;

import java.math.BigInteger;

public class SequentialFactorialCalculator implements FactorialCalculator {

    public BigInteger calculate(int number) {
        if (number == 1) {
            return BigInteger.ONE;
        }

        BigInteger result = BigInteger.ONE;

        for (int i = 2; i <= number; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }

        return result;
    }
}
