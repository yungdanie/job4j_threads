package ru.job4j.concurrent.test;

import java.util.concurrent.CompletableFuture;

public class Testing {

    public static CompletableFuture<Integer> get() {
        return CompletableFuture.supplyAsync(() -> {
            return 1;
        });
    }

    public static void main(String[] args) throws Exception {
        ThreadLocal<Integer> a = new ThreadLocal<>();
        System.out.println(a.get());
    }
}
