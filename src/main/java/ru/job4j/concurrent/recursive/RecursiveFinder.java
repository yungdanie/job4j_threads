package ru.job4j.concurrent.recursive;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class RecursiveFinder<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final int from;
    private final int to;
    private final T etalon;

    private RecursiveFinder(T[] array, int from, int to, T etalon) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.etalon = etalon;
    }

    @Override
    protected Integer compute() {
        if ((to - from) < 10) {
            for (int i = from; i <= to; i++) {
                if (etalon.equals(array[i])) {
                    return i;
                }
            }
            return -1;
        }
        int mid = (from + to) / 2;
        RecursiveFinder<T> left = new RecursiveFinder<>(array, from, mid, etalon);
        RecursiveFinder<T> right = new RecursiveFinder<>(array, mid + 1, to, etalon);
        left.fork();
        right.fork();
        int result1 = left.join();
        int result2 = right.join();
        return Math.max(result1, result2);
    }

    public static <T> int getIndex(T[] array, T etalon) {
        if (array == null || etalon == null) {
            throw new IllegalArgumentException();
        }
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int result = forkJoinPool.invoke(new RecursiveFinder<>(array, 0, array.length - 1, etalon));
        if (result == -1) {
            throw new IllegalArgumentException();
        }
        return result;
    }
}
