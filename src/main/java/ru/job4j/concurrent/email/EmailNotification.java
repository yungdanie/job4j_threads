package ru.job4j.concurrent.email;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class EmailNotification {
    private final ExecutorService pool = Executors.newCachedThreadPool();

    public void send(String subject, String body, String email) {

    }

    public void emailTo(User user) {
        pool.submit(() -> send(user.email(), "body", user.email()));
    }

    public void close() {
        pool.shutdown();
    }
}
