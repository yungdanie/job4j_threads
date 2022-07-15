package ru.job4j.concurrent.queue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    private int size;
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
                    throw new RuntimeException(e);
                }
            }
            queue.offer(value);
            size++;
            this.notify();
        }
    }

    public T poll() {
        synchronized (this) {
            T returnObject;
            while (queue.isEmpty()) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            returnObject = queue.poll();
            size--;
            this.notify();
            return returnObject;
        }
    }

}
