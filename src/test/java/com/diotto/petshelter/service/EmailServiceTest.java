package com.diotto.petshelter.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock private JavaMailSender mailSender;
    @InjectMocks
    private EmailService emailService;

    @Test
    @DisplayName("Should build and try to send e-mail with correct data")
    void sendNewPetNotification_shouldBuildAndSendCorrectEmail() {
        //ARRANGE - data for test
        String testRecipientEmail = "test@adopter.com";
        String testMessageBody = "Pet Name: Clara Bow, Type: CAT, Breed: British Shorthair";

        //Create ArgumentCaptor to capture SimpleMailMessage object
        ArgumentCaptor<SimpleMailMessage> mailMessageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        // ACT
        emailService.sendNewPetCreatedEmail(testRecipientEmail, testMessageBody);

        // ASSERT - check if method was called once and capture passed argument at the same time
        verify(mailSender).send(mailMessageCaptor.capture());

        SimpleMailMessage capturedMessage = mailMessageCaptor.getValue();

        //Assert that the content of the captured message is exactly what expected
        assertThat(capturedMessage.getTo()).containsExactly(testRecipientEmail);
        assertThat(capturedMessage.getSubject()).isEqualTo("New pet created! 🐱 🐶 \n");
        assertThat(capturedMessage.getText()).isEqualTo("Thank you for registering a new pet. \n" + testMessageBody);

    }
}