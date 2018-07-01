package com.notes.services.mail;

import it.ozimov.springboot.mail.model.Email;
import it.ozimov.springboot.mail.model.defaultimpl.DefaultEmail;
import it.ozimov.springboot.mail.service.EmailService;
import it.ozimov.springboot.mail.service.exception.CannotSendEmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Collections;
import java.util.Map;

@Service
public class MailService {

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    private EmailService emailService;

    public void sendSimpleMessage(String to, String from, String subject, String messageText) {
        mailSender.send(new SimpleMailMessage() {{
           setTo(to);
           setFrom(from);
           setSubject(subject);
           setText(messageText);
        }});
    }

    public void sendTemplatedMessage(String to, String from, String subject, String template, Map<String, Object> model) {

        try {
            final Email email = DefaultEmail.builder()
                .from(new InternetAddress(from))
                .to(Collections.singleton(new InternetAddress(to)))
                .subject(subject)
                .body("")//this will be overridden by the template, anyway
                .encoding("UTF-8").build();
            emailService.send(email, template, model);
        } catch (AddressException | CannotSendEmailException e) {
            e.printStackTrace();
        }
    }
}
