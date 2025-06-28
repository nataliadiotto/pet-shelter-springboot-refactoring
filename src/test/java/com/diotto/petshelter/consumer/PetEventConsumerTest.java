package com.diotto.petshelter.consumer;

import com.diotto.petshelter.service.EmailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PetEventConsumerTest {
    @Mock private EmailService emailService;

    @InjectMocks
    private PetEventConsumer petEventConsumer;

    @Test
    @DisplayName("Should call EmailService when a message for pet created is received")
    void onPetCreated_shouldCallEmailService() {
        // ARRANGE
        String incomingMessage = "New Cat registered: Whiskers...";

        // ACT
        petEventConsumer.handlePetRegisteredEvent(incomingMessage);

        // ASSERT
        verify(emailService).sendNewPetCreatedEmail(
                "diottodev.test@gmail.com",
                incomingMessage);
    }
}