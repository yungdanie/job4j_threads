package ru.job4j.concurrent.cache;

import org.junit.jupiter.api.Test;


import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CacheTest {

    @Test
    public void whenAddToCacheAndDeleteById() {
        Cache cache = new Cache();
        cache.add(new Base("Name", 1, 1));
        cache.add(new Base("Name2", 2, 1));
        assertTrue(cache.delete(1));
        assertTrue(cache.delete(2));
    }

    @Test
    public void whenAddToCacheAndDeleteByObject() {
        Cache cache = new Cache();
        Base base1 = new Base("Name", 1, 1);
        Base base2 = new Base("Name2", 2, 1);
        cache.add(base1);
        cache.add(base2);
        assertTrue(cache.delete(base1));
        assertTrue(cache.delete(base2));
    }

    @Test
    public void whenThrowException() {
        assertThrows(OptimisticException.class, () -> {
            Cache cache = new Cache();
            Base oldBase = new Base("Name", 1, 1);
            Base updBase = new Base("New Name", 1, 2);
            cache.add(oldBase);
            cache.update(updBase);
        });
    }

    @Test
    public void whenFewThreadsAndAddToCacheAndDeleteById() {
        Cache cache = new Cache();
        Thread thread1 = new Thread(() -> cache.add(new Base("Name", 1, 1)));

        Thread thread2 = new Thread(() -> cache.add(new Base("Name2", 2, 1)));
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(cache.delete(1));
        assertTrue(cache.delete(2));
    }

    @Test
    public void whenAddSameObjectInOtherThread() {
        CopyOnWriteArrayList<Boolean> result = new CopyOnWriteArrayList<>();
        Cache cache = new Cache();
        Base base = new Base("Name", 1, 1);
        Thread thread1 = new Thread(() -> result.add(cache.add(base)));
        Thread thread2 = new Thread(() -> result.add(cache.add(base)));
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(result.contains(Boolean.TRUE));
    }
}