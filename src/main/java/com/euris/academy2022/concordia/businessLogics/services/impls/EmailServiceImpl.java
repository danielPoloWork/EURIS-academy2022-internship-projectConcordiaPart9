package com.euris.academy2022.concordia.businessLogics.services.impls;

import com.euris.academy2022.concordia.businessLogics.services.ConfigurationService;
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


    private final ConfigurationService configurationService;

    public EmailServiceImpl(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    public void send() {

EmailDto emailDto= EmailDto.builder()
        .receiverAddress(configurationService.getByLabel("EMAIL_RECEIVER").getBody().getValue())
        .senderAddress(configurationService.getByLabel("CONCORDIA_EMAIL_ADDRESS").getBody().getValue())
        .senderUsername(configurationService.getByLabel("CONCORDIA_SENDER_EMAIL_APP_USERNAME").getBody().getValue())
        .senderPassword(configurationService.getByLabel("CONCORDIA_SENDER_EMAIL_APP_PASSWORD").getBody().getValue())
        .text(configurationService.getByLabel("EMAIL_TEXT").getBody().getValue())
        .subject(configurationService.getByLabel("EMAIL_SUBJECT").getBody().getValue()).build();



        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailDto.getSenderUsername(), emailDto.getSenderPassword());
            }
        });

        MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(emailDto.getSenderAddress()));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailDto.getReceiverAddress()));


            msg.setSubject(emailDto.getSubject());

            Multipart emailContent = new MimeMultipart();
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(emailDto.getText());
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
