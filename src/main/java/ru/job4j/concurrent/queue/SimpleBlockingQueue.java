package ru.job4j.concurrent.queue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private int size;
    @GuardedBy("this")
    private final int capacity;
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    public SimpleBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public void offer(T value) {
        synchronized (this) {
            while (size >= capacity) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            queue.offer(value);
            size++;
            this.notify();
        }
    }

    public T poll() {
        synchronized (this) {
            T returnObject = null;
            while (queue.isEmpty() && !Thread.currentThread().isInterrupted()) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            if (!Thread.currentThread().isInterrupted()) {
                returnObject = queue.poll();
                size--;
                this.notify();
            }
            return returnObject;
        }
    }

}
