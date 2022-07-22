package ru.job4j.concurrent.threadpool;

import ru.job4j.concurrent.queue.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class ThreadPool {

    private final static int SIZE = Runtime.getRuntime().availableProcessors();

    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(SIZE);

    public ThreadPool() {
        IntStream.range(0, SIZE).forEach(x -> threads.add(new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    tasks.poll().run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        })));
        threads.forEach(Thread::start);
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        threads.forEach(Thread::interrupt);
    }
}
