package ru.job4j.concurrent.server;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {

    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> map = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        Resp resp = null;
        if ("GET".equals(req.httpRequestType())) {
            ConcurrentLinkedQueue<String> queue = map.get(req.getSourceName());
            if (queue != null) {
                resp = new Resp(queue.poll(), "200 OK");
            }
        } else if ("POST".equals(req.httpRequestType())) {
            ConcurrentLinkedQueue<String> linkedQueue = map.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
            if (linkedQueue == null) {
                linkedQueue = map.get(req.getSourceName());
            }
            linkedQueue.add(req.getParam());
            resp = new Resp("", "200 OK");
        }
        return resp != null ? resp : new Resp("", "204 No Content");
    }

}
