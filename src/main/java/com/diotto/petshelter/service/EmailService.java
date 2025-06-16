package com.diotto.petshelter.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendNewPetCreatedEmail(String to, String body) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject("New pet created! \uD83D\uDC31 \uD83D\uDC36");
        mailMessage.setText("Thank you for registering a new pet. \n" + body);

        mailSender.send(mailMessage);

        System.out.println("New pet created email sent to: " + to);
    }
}
