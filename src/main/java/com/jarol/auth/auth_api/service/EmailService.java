package com.jarol.auth.auth_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.tools.JavaFileManager;

@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService{

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String mail;

    @Async
    @Override
    public void sendEmail(String toEmail, String verificationLink) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mail);
        message.setTo(toEmail);
        message.setSubject("Verify your email");
        message.setText("Thanks for sign up, Please click on the link to verify your account "+verificationLink);

        mailSender.send(message);
    }
}
