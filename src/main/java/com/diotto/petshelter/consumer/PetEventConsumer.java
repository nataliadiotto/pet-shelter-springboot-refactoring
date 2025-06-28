package com.diotto.petshelter.consumer;

import com.diotto.petshelter.config.RabbitMQConfig;
import com.diotto.petshelter.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PetEventConsumer {

    private final EmailService emailService;

    public PetEventConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void handlePetRegisteredEvent(String message) {
        System.out.println("Message received from queue: " + message);
        emailService.sendNewPetCreatedEmail("diottodev.test@gmail.com", message);
    }


}
