package ru.job4j.concurrent.server;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Req {

    private static final String SEP = System.lineSeparator();
    private static final String WHITE_SPACE = " ";
    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        Map<String, String> map = new HashMap<>();
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException();
        }
        Arrays.stream(content.split(SEP)).forEach(x -> {
            if (x.startsWith("GET") || x.startsWith("POST")) {
                String[] startArray = x.split(WHITE_SPACE);
                map.put("method", startArray[0]);
                if (startArray[1].startsWith("/queue") || startArray[1].startsWith("/topic")) {
                    String[] uri = startArray[1].split("/");
                    map.put("mode", uri[1]);
                    map.put("source", uri[2]);
                    if (uri.length >= 4) {
                        map.put("param", uri[3]);
                    }
                }
            } else if (x.startsWith("temperature=")) {
                map.put("param", x);
            }
        });
        return new Req(map.get("method"), map.get("mode"),
                map.get("source"), map.getOrDefault("param", ""));
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}
