package ru.job4j.concurrent.server;

public interface Service {
    Resp process(Req req);
}
