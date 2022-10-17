package com.euris.academy2022.concordia.businessLogics.controllers;

import com.euris.academy2022.concordia.businessLogics.services.impls.PdfServiceImpl;
import net.sf.jasperreports.engine.JRException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {


    private final PdfServiceImpl pdfService;

    public PdfController(PdfServiceImpl pdfService) {

        this.pdfService = pdfService;
    }

    @GetMapping("/tasks")
    public boolean tasks() throws JRException, FileNotFoundException {

        return pdfService.taskAdvancement();


    }


    @GetMapping("/performances")
    public boolean performances() throws JRException, FileNotFoundException {

        return pdfService.memberPerformanceReport();
    }

}
