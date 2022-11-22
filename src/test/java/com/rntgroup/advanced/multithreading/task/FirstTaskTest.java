package com.rntgroup.advanced.multithreading.task;
import com.rntgroup.advanced.multithreading.task.factorial.ParallelFactorialCalculator;
import com.rntgroup.advanced.multithreading.task.factorial.SequentialFactorialCalculator;
import org.junit.Test;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FirstTaskTest {

    @Test
    public void performanceTest() {
        var parallel = new ParallelFactorialCalculator();
        var sequential = new SequentialFactorialCalculator();

        /**
         * Начиная с ~9000 скорость вычисления при помощи FJP начинает превышать последовательный расчёт
         */
        int number = 20000;

        LocalDateTime parallelStart = LocalDateTime.now();
        parallel.calculate(number);
        LocalDateTime parallelFinish = LocalDateTime.now();

        LocalDateTime sequentialStart = LocalDateTime.now();
        sequential.calculate(number);
        LocalDateTime sequentialFinish = LocalDateTime.now();

        Duration parallelDuration = Duration.between(parallelStart, parallelFinish);
        Duration sequentialDuration = Duration.between(sequentialStart, sequentialFinish);

        System.out.println("Время при параллельном расчёте = " + parallelDuration.toMillis() + " мс");
        System.out.println("Время при последовательном расчёте = " + sequentialDuration.toMillis() + " мс");
    }

    @Test
    public void parallelFactorialCalculatorTest() {
        var parallel = new ParallelFactorialCalculator();

        assertEquals(BigInteger.valueOf(120), parallel.calculate(5));
    }

    @Test
    public void sequentialFactorialCalculatorTest() {
        var sequential = new SequentialFactorialCalculator();

        assertEquals(BigInteger.valueOf(120), sequential.calculate(5));
    }
}
