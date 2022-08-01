package ru.job4j.concurrent.server;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QueueServiceTest {

    @Test
    public void whenPostThenGetQueue() {
        QueueService queueService = new QueueService();
        String paramForPostMethod = "temperature=18";

        queueService.process(
                new Req("POST", "queue", "weather", paramForPostMethod)
        );

        Resp result = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        assertThat(result.text()).isEqualTo("temperature=18");
    }

    @Test
    public void whenPostAFewAndGet() {
        QueueService queueService = new QueueService();
        String param1 = "temperature=18";
        String param2 = "temperature=13";
        String param3 = "temperature=18";

        queueService.process(
                new Req("POST", "queue", "weather", param1)
        );

        queueService.process(
                new Req("POST", "queue", "weather", param2)
        );

        queueService.process(
                new Req("POST", "queue", "weather", param3)
        );

        Resp result = queueService.process(new Req("GET", "queue", "weather",null));
        assertThat(result.text()).isEqualTo("temperature=18");

        result = queueService.process(new Req("GET", "queue", "weather",null));
        assertThat(result.text()).isEqualTo("temperature=13");

        result = queueService.process(new Req("GET", "queue", "weather",null));
        assertThat(result.text()).isEqualTo("temperature=18");
    }
}