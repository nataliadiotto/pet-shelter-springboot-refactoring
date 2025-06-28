package com.diotto.petshelter.publisher;

import com.diotto.petshelter.config.RabbitMQConfig;
import com.diotto.petshelter.domain.DTO.PetResponseDTO;
import com.diotto.petshelter.domain.enums.PetType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class PetEventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(PetEventPublisher.class);
    private final AmqpTemplate amqpTemplate;
    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routingkey.pet-created}")
    private String routingKey;

    public PetEventPublisher(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void publishPetCreatedEvent(PetResponseDTO dto) {
        String message = String.format("New %s registered: %s\n" +
                "Address: %s", dto.getPetType(), dto.getFullName(), dto.getAddress());

        amqpTemplate.convertAndSend(
                exchangeName,
                routingKey,
                message
        );

        logger.info("Message sent to exchange '{}' with routing key '{}'", exchangeName, routingKey);    }

}
