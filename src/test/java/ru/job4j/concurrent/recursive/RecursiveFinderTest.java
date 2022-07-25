package ru.job4j.concurrent.recursive;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.stream.IntStream;

class RecursiveFinderTest {

    @Test
    void whenRecursiveSearchObjectInArray() {
        final int arraySize = 100;
        Object etalon = new Object();
        Object[] objects = new Object[arraySize + 1];
        IntStream.range(0, arraySize + 1).forEach(x -> objects[x] = new Object());
        int expected = new Random().nextInt(arraySize);
        objects[expected] = etalon;
        IndexFinder<Object> indexFinder = new IndexFinder<>();
        int result = indexFinder.getIndex(objects, etalon);
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    void whenRecursiveSearchIntegerInArray() {
        final int arraySize = 100;
        final Random random = new Random();
        Integer etalon = random.nextInt();
        Integer[] numbers = new Integer[arraySize + 1];
        IntStream.range(0, arraySize + 1).forEach(x -> numbers[x] = random.nextInt());
        int expected = random.nextInt(arraySize);
        numbers[expected] = etalon;
        IndexFinder<Integer> indexFinder = new IndexFinder<>();
        int result = indexFinder.getIndex(numbers, etalon);
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    void whenLinearSearchIntegerInArray() {
        final int arraySize = 5;
        final Random random = new Random();
        Integer etalon = random.nextInt();
        Integer[] numbers = new Integer[arraySize + 1];
        IntStream.range(0, arraySize + 1).forEach(x -> numbers[x] = random.nextInt());
        int expected = random.nextInt(arraySize);
        numbers[expected] = etalon;
        IndexFinder<Integer> indexFinder = new IndexFinder<>();
        int result = indexFinder.getIndex(numbers, etalon);
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    void whenCanNotFindEtalon() {
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            final int arraySize = 100;
            final Random random = new Random();
            Integer etalon = 101;
            Integer[] numbers = new Integer[arraySize + 1];
            IntStream.range(0, arraySize + 1).forEach(x -> numbers[x] = random.nextInt(100));
            IndexFinder<Integer> indexFinder = new IndexFinder<>();
            indexFinder.getIndex(numbers, etalon);
        });
    }

    @Test
    void whenInitArrayAsNull() {
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            IndexFinder<Integer> indexFinder = new IndexFinder<>();
            indexFinder.getIndex(null, 1);
        });
    }

    @Test
    void whenInitEtalonAsNull() {
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            IndexFinder<Integer> indexFinder = new IndexFinder<>();
            indexFinder.getIndex(new Integer[] {1, 2, 3}, null);
        });
    }
}