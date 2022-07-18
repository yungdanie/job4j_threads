package ru.job4j.concurrent.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CacheTest {

    @Test
    public void whenAddToCacheAndDelete() {
        Cache cache = new Cache();
        cache.add(new Base("Name", 1, 1));
        cache.add(new Base("Name2", 2, 1));
        assertTrue(cache.delete(1));
        assertTrue(cache.delete(2));
    }
}