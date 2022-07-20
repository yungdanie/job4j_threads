package ru.job4j.concurrent.cascount;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;


class CASCountTest {

    @Test
    public void whenFewThreadIncrementAndGet() {
        CASCount casCount = new CASCount();
        Thread thread1 = new Thread(() -> IntStream.of(1, 2, 3).forEach(x -> casCount.increment()));
        Thread thread2 = new Thread(() -> IntStream.of(4, 5, 6).forEach(x -> casCount.increment()));
        Thread thread3 = new Thread(() -> IntStream.of(7, 8, 9).forEach(x -> casCount.increment()));
        thread1.start();
        thread2.start();
        thread3.start();
        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Assertions.assertThat(casCount.get()).isEqualTo(9);
    }

    @Test
    public void whenIncrementInOneThreadAndGet() {
        CASCount casCount = new CASCount();
        Thread thread1 = new Thread(() -> IntStream.of(1, 2, 3).forEach(x -> casCount.increment()));
        thread1.start();
        try {
            thread1.join();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Assertions.assertThat(casCount.get()).isEqualTo(3);
    }

    @Test
    public void whenInitializationValueIs0() {
        CASCount casCount = new CASCount();
        Thread thread1 = new Thread(() -> Assertions.assertThat(casCount.get()).isEqualTo(0));
        thread1.start();
        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}