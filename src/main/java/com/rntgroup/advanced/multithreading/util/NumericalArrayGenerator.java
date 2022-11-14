package com.rntgroup.advanced.multithreading.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.security.SecureRandom;

@UtilityClass
public class NumericalArrayGenerator {

    public int[] getArrayForFactorial(int number) {
        int[] array = new int[number];

        for (int i = 0; i < number; i++) {
            array[i] = i+1;
        }

        return array;
    }

    @SneakyThrows
    public int[] getRandomArray(int size) {
        return SecureRandom.getInstanceStrong().ints(size).toArray();
    }
}
