package com.rntgroup.advanced.multithreading.task.factorial;

import com.rntgroup.advanced.multithreading.util.NumericalArrayGenerator;

import java.math.BigInteger;
import java.util.concurrent.ForkJoinPool;

public class ParallelFactorialCalculator implements FactorialCalculator {

    public BigInteger calculate(int number) {
        int[] array = NumericalArrayGenerator.getArrayForFactorial(number);

        FactorialCalculatorTask factorialTask = new FactorialCalculatorTask(array);

        return new ForkJoinPool().invoke(factorialTask);
    }
}
