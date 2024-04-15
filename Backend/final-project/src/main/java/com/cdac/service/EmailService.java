
package com.cdac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.cdac.dto.Email;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendThankYouEmail(String e) {
        Email email = new Email();
        email.setReceipt(e);
        String name[] = e.split("@");
        email.setSubject("Thank You for Booking Our Services !");
        email.setBody("Dear " + name[0] + ",\n\n"
                + "Thank you for booking our services!\n\n"
                + "We are excited to serve you.\n\n"
                + "Best regards,\n"
                + "Your Service Provider-Pathlab ");

        sendEmail(email);
    }

    private void sendEmail(Email email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email.getReceipt());
        mailMessage.setSubject(email.getSubject());
        mailMessage.setText(email.getBody());

        javaMailSender.send(mailMessage);
    }
}