package org.vesuviano.dieti_estates.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public abstract class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) 
    {
        this.mailSender = mailSender;
    }

    protected void sendEmail(String to, String subject, String body) 
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}
