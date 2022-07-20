package ru.job4j.concurrent.queue;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final int capacity;
    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    public SimpleBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public void offer(T value) throws InterruptedException {
        synchronized (this) {
            while (queue.size() >= capacity) {
                this.wait();
            }
            queue.offer(value);
            this.notify();
        }
    }

    public T poll() throws InterruptedException {
        synchronized (this) {
            T returnObject = null;
            while (queue.isEmpty()) {
                this.wait();
            }
            returnObject = queue.poll();
            this.notify();
            return returnObject;
        }
    }

    public boolean isEmpty() {
        synchronized (this) {
            return queue.isEmpty();
        }
    }

}
