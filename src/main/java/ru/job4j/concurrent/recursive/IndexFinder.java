package ru.job4j.concurrent.recursive;

import java.util.concurrent.ForkJoinPool;

public class IndexFinder<T> {

    public int getIndex(T[] array, T etalon) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int result = forkJoinPool.invoke(new RecursiveFinder<>(array, 0, array.length - 1, etalon));
        if (result == -1) {
            throw new IllegalArgumentException();
        }
        return result;
    }
}
