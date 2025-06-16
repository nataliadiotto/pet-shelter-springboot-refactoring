package com.diotto.petshelter.producer;

import com.diotto.petshelter.config.RabbitMQConfig;
import com.diotto.petshelter.domain.entity.Pet;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class PetEventProducer {

    private final RabbitTemplate rabbitTemplate;

    public PetEventProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishCreatedEvent(Pet pet) {
        String message = String.format("New registered pet: %s %s (%s)", pet.getFirstName(), pet.getLastName(), pet.getPetType());
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE, message);
        System.out.println("Message sent to queue: " + message);
    }

}
