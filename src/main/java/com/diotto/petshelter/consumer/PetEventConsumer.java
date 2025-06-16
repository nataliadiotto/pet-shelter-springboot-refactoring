package com.diotto.petshelter.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PetEventConsumer {

    @RabbitListener(queues = "pet.created.queue")
    public void handlePetRegisteredEvent(String message) {
        System.out.println("Message received from queue: " + message);
    }


}
