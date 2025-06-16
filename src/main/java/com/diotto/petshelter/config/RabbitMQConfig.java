package com.diotto.petshelter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "pet.created.queue";

    @Bean
    public Queue petCreatedQueue() {
        return new Queue(QUEUE_NAME, false);
    }
}
