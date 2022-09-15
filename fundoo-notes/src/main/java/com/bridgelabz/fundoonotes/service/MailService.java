package com.bridgelabz.fundoonotes.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 *
 * @author : Ashwini Rathod
 * @version: 1.0
 * @since  : 09-09-2022
 * Purpose : Mail Service Used To Send An Mail
 *
 */

@Component
@Slf4j
public class MailService {

    /**
     *
     * @author : Ashwini Rathod
     * @version: 1.0
     * @since  : 09-09-2022
     * Purpose: Creating method to send Email
     *
     */
    public static void send(String toEmail, String body, String subject) {
        final String fromEmail = System.getenv("Email");
        final String password = System.getenv("Password");
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };

        javax.mail.Session session = Session.getInstance(properties, authenticator);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setHeader("Content-Type", "text/HTML;charset=UTF-8");
            message.setHeader("format", "flowed");
            message.setHeader("Content-Transfer-Encoding", "8bit");
            message.setFrom(new InternetAddress("no_reply@gmail.com", "NoReply"));
            message.setReplyTo(InternetAddress.parse(System.getenv("Email"), false));
            message.setSubject(subject, "UTF-8");
            message.setText(body, "UTF-8");
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            message.setSentDate(new Date());
            Transport.send(message);
            log.info("Email sent Successfully.....!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
