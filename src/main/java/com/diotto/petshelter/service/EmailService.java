package com.diotto.petshelter.service;

import com.diotto.petshelter.publisher.PetEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendNewPetCreatedEmail(String recipientEmail, String body) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(recipientEmail);
        mailMessage.setSubject("New pet created! \uD83D\uDC31 \uD83D\uDC36 \n");
        mailMessage.setText("Thank you for registering a new pet. \n" + body);

        mailSender.send(mailMessage);

        logger.info("New pet created. Email sent to: '{}'", recipientEmail);
    }
}
