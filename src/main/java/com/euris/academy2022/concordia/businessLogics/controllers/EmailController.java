package com.euris.academy2022.concordia.businessLogics.controllers;

import com.euris.academy2022.concordia.businessLogics.services.EmailService;
import com.euris.academy2022.concordia.businessLogics.services.impls.PdfServiceImpl;
import net.sf.jasperreports.engine.JRException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api/email")
public class EmailController {


    private final EmailService emailService;

    public EmailController(EmailService emailService) {

        this.emailService=emailService;
    }

    @GetMapping
    public void send(){

        emailService.send();

    }

}
