package ru.job4j.concurrent.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(),
                (x, y) -> {
                if (memory.get(model.getId()).getVersion() != model.getVersion()) {
                    throw new OptimisticException("Versions are not equal");
                } else {
                    return new Base(model.getName(), x, y.getVersion() + 1);
                }
        }) != null;
    }

    public boolean delete(Base model) {
        return memory.remove(model.getId()) != null;
    }

    public boolean delete(int id) {
        return memory.remove(id) != null;
    }
}