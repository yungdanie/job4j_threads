package ru.job4j.concurrent.queue;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleBlockingQueueTest {

    @Test
    public void whenAddToQueueAndGetFromAnotherThread() throws InterruptedException {
        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>(10);
        Thread thread1 = new Thread(() -> {
            try {
                simpleBlockingQueue.offer(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread thread2 = new Thread(() -> {
            Integer res = null;
            try {
                simpleBlockingQueue.offer(1);
                res = simpleBlockingQueue.poll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            assertThat(res).isEqualTo(1);
        });
        thread2.start();
        thread1.start();
        thread1.join();
        thread2.join();
    }

    @Test
    public void whenAddToQueueAndWaitToOffer() throws InterruptedException {
        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>(1);
        Thread thread1 = new Thread(() -> {
            try {
                simpleBlockingQueue.offer(1);
                simpleBlockingQueue.offer(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                Integer res = simpleBlockingQueue.poll();
                assertThat(res).isEqualTo(1);
                res = simpleBlockingQueue.poll();
                assertThat(res).isEqualTo(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread2.start();
        thread1.start();
        thread1.join();
        thread2.join();
    }

}