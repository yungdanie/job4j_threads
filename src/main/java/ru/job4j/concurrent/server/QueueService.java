package ru.job4j.concurrent.server;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {

    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> map = new ConcurrentHashMap<>();

    @Override
    public Resp process(Req req) {
        Resp resp = new Resp("", "501 Not Implemented");
        if ("GET".equals(req.httpRequestType())) {
            ConcurrentLinkedQueue<String> queue = map.get(req.getSourceName());
            if (queue != null) {
                var res = queue.poll();
                resp = new Resp(res != null ? res : "", "200 OK");
            }
        } else if ("POST".equals(req.httpRequestType())) {
            map.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
            map.get(req.getSourceName()).add(req.getParam());
            resp = new Resp("", "200 OK");
        }
        return resp;
    }

}
