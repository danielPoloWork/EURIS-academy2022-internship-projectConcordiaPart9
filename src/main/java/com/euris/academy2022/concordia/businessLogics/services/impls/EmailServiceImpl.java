package com.euris.academy2022.concordia.businessLogics.services.impls;

import com.euris.academy2022.concordia.businessLogics.services.EmailService;
import com.euris.academy2022.concordia.dataPersistences.DTOs.EmailDto;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService {


    public void send(EmailDto email) {


        String senderUsername = email.getSenderUsername();
        String senderPassword = email.getSenderPassword();
        String text = email.getText();
        String subject = email.getSubject();
        String senderAddress = email.getSenderAddress();
        String receiverAddress = email.getReceiverAddress();

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderUsername, senderPassword);
            }
        });

        MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(senderAddress));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverAddress));


            msg.setSubject(subject);

            Multipart emailContent = new MimeMultipart();
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(text);
            MimeBodyPart pdfTasks = new MimeBodyPart();
            pdfTasks.attachFile("tasks.pdf");
            MimeBodyPart pdfPerformances = new MimeBodyPart();
            pdfPerformances.attachFile("performances.pdf");
            emailContent.addBodyPart(pdfPerformances);
            emailContent.addBodyPart(textBodyPart);
            emailContent.addBodyPart(pdfTasks);

            msg.setContent(emailContent);
            Transport.send(msg);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
