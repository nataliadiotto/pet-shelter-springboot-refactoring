package com.diotto.petshelter.publisher;

import com.diotto.petshelter.domain.DTO.PetResponseDTO;
import com.diotto.petshelter.domain.entity.Pet;
import com.diotto.petshelter.domain.enums.BiologicalSex;
import com.diotto.petshelter.domain.enums.PetType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PetEventPublisherTest {

    @Mock private AmqpTemplate amqpTemplate;

    @InjectMocks
    private PetEventPublisher petEventPublisher;

    private final String testExchange = "pets_exchange_test";
    private final String testRoutingKey = "pet.created.key_test";

    @BeforeEach
    void setUp() {

        ReflectionTestUtils.setField(petEventPublisher, "exchangeName", testExchange);
        ReflectionTestUtils.setField(petEventPublisher, "routingKey", testRoutingKey);
    }


    @Test
    @DisplayName("Should publish formatted message for the correct exchange and routing key")
    void publishPetCreatedEvent_shouldSendMessageToCorrectDestination() {
        // ARRANGE
        Pet testPet = new Pet();
        testPet.setId(1L);
        testPet.setFirstName("Sweet");
        testPet.setLastName("Nothing");
        testPet.setPetType(PetType.CAT);
        testPet.setBiologicalSex(BiologicalSex.FEMALE);
        testPet.setZipCode("13484339");
        testPet.setAddressNumber(1313);
        testPet.setStreetName("Meow Street");
        testPet.setAddressCity("MeowTown");
        testPet.setState("NY");
        testPet.setAge(3.0);
        testPet.setWeight(8.0);
        testPet.setBreed("Scottish Fold");
        PetResponseDTO testPetDto = new PetResponseDTO(testPet);

        String expectedMessageBody = String.format("New %s registered: %s\n" +
                "Address: %s", testPetDto.getPetType(), testPetDto.getFullName(), testPetDto.getAddress());


        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

        // ACT
        petEventPublisher.publishPetCreatedEvent(testPetDto);

        // ASSERT
        verify(amqpTemplate).convertAndSend(
                eq(testExchange),
                eq(testRoutingKey),
                messageCaptor.capture()
        );

        String capturedMessage = messageCaptor.getValue();
        assertThat(capturedMessage).isEqualTo(expectedMessageBody);
    }
}