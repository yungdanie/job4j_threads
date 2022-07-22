package ru.job4j.concurrent.recursive;

import java.util.concurrent.RecursiveTask;

public class RecursiveFinder<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final int from;
    private final int to;

    private final T etalon;

    public RecursiveFinder(T[] array, int from, int to, T etalon) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.etalon = etalon;
    }

    @Override
    protected Integer compute() {
        if ((to - from) < 5) {
            for (int i = from; i <= to; i++) {
                if (array[i].equals(etalon)) {
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
        return result1 != -1 ? result1 : result2;
    }
}
