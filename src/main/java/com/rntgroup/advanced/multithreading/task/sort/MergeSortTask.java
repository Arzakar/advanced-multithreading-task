package com.rntgroup.advanced.multithreading.task.sort;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MergeSortTask extends RecursiveTask<int[]> {

    int[] array;

    @Override
    protected int[] compute() {
        if (array.length == 1) {
            return array;
        }

        var leftHalf = new MergeSortTask(Arrays.copyOfRange(array, 0, array.length / 2));
        var rightHalf = new MergeSortTask(Arrays.copyOfRange(array, array.length / 2, array.length));

        leftHalf.fork();
        rightHalf.fork();

        return merge(leftHalf.join(), rightHalf.join());
    }

    private int[] merge(int[] firstArray, int[] secondArray) {
        int mergedArraySize = firstArray.length + secondArray.length;
        int[] mergedArray = new int[mergedArraySize];

        int firstPointer = 0;
        int secondPointer = 0;
        int iterator = 0;

        while(firstPointer < firstArray.length && secondPointer < secondArray.length) {
            mergedArray[iterator++] = firstArray[firstPointer] < secondArray[secondPointer]
                    ? firstArray[firstPointer++]
                    : secondArray[secondPointer++];
        }

        while(firstPointer < firstArray.length) {
            mergedArray[iterator++] = firstArray[firstPointer++];
        }

        while(secondPointer < secondArray.length) {
            mergedArray[iterator++] = secondArray[secondPointer++];
        }

        return mergedArray;
    }
}
