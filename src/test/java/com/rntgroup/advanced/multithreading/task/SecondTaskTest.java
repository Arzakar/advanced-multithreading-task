package com.rntgroup.advanced.multithreading.task;

import com.rntgroup.advanced.multithreading.task.sort.ParallelMergeSort;
import com.rntgroup.advanced.multithreading.util.NumericalArrayGenerator;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class SecondTaskTest {

    @Test
    public void sortPerformanceTest() {
        int[] unsortedArray = NumericalArrayGenerator.getRandomArray(500_000);

        LocalDateTime parallelStart = LocalDateTime.now();
        new ParallelMergeSort().sort(unsortedArray);
        LocalDateTime parallelFinish = LocalDateTime.now();

        LocalDateTime internalStart = LocalDateTime.now();
        Arrays.stream(unsortedArray).sorted().toArray();
        LocalDateTime internalFinish = LocalDateTime.now();

        Duration parallelDuration = Duration.between(parallelStart, parallelFinish);
        Duration sequentialDuration = Duration.between(internalStart, internalFinish);

        System.out.println("Время при параллельной сортировке = " + parallelDuration.toMillis() + " мс");
        System.out.println("Время при java-сортировке = " + sequentialDuration.toMillis() + " мс");
    }

    @Test
    public void mergeSortTest() {
        int[] sortedByMergeSort = new ParallelMergeSort().sort(new int[]{5, 1, 2, 7, 2, 3});
        int[] actual = {1, 2, 2, 3, 5, 7};

        for (int i = 0; i < actual.length; i++) {
            assertEquals(actual[i], sortedByMergeSort[i]);
        }
    }

}
