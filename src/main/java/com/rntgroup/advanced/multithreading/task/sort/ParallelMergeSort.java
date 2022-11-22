package com.rntgroup.advanced.multithreading.task.sort;

import java.util.concurrent.ForkJoinPool;

public class ParallelMergeSort {

    public int[] sort(int[] unsortedArray) {
        MergeSortTask mergeSortTask = new MergeSortTask(unsortedArray);

        return new ForkJoinPool().invoke(mergeSortTask);
    }

}
