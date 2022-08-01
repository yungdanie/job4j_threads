package ru.job4j.concurrent.server;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class TopicServiceTest {

    @Test
    public void whenOneClientSubscribe() {
        TopicService topicService = new TopicService();
        String paramForPublisher = "temperature=18";
        String paramForSubscriber1 = "client407";
        String paramForSubscriber2 = "client6565";
        topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        topicService.process(
                new Req("POST", "topic", "weather", paramForPublisher)
        );
        Resp result1 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        Resp result2 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber2)
        );
        Assertions.assertThat(result1.text()).isEqualTo("temperature=18");
        Assertions.assertThat(result2.text()).isEqualTo("");
    }

    @Test
    public void whenTwoClientsSubscribe() {
        TopicService topicService = new TopicService();
        String paramForPublisher = "temperature=18";
        String paramForSubscriber1 = "client407";
        String paramForSubscriber2 = "client6565";
        topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber2)
        );
        topicService.process(
                new Req("POST", "topic", "weather", paramForPublisher)
        );
        Resp result1 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        Resp result2 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber2)
        );
        Assertions.assertThat(result1.text()).isEqualTo("temperature=18");
        Assertions.assertThat(result2.text()).isEqualTo("temperature=18");
    }

    @Test
    public void whenTwoClientsSubscribeThenPollDiffInfo() {
        TopicService topicService = new TopicService();
        String publ1 = "temperature=18";
        String publ2 = "temperature=13";
        String paramForSubscriber1 = "client407";
        String paramForSubscriber2 = "client6565";
        topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber2)
        );
        topicService.process(
                new Req("POST", "topic", "weather", publ1)
        );
        topicService.process(
                new Req("POST", "topic", "weather", publ2)
        );
        Resp result1 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        Assertions.assertThat(result1.text()).isEqualTo("temperature=18");
        result1 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        Assertions.assertThat(result1.text()).isEqualTo("temperature=13");


        Resp result2 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber2)
        );
        Assertions.assertThat(result2.text()).isEqualTo("temperature=18");
        result2 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber2)
        );
        Assertions.assertThat(result2.text()).isEqualTo("temperature=13");

    }
}