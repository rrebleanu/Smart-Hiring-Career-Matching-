package com.project.demo.service;

import net.sourceforge.tess4j.Tesseract;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;

@Service
public class OCRService {

    public String extrageTextDinCV(MultipartFile fisierMultipart) {
        String numeFisier = fisierMultipart.getOriginalFilename();

        try {
            // Dacă utilizatorul încarcă un PDF, folosim PDFBox
            if (numeFisier != null && numeFisier.toLowerCase().endsWith(".pdf")) {
                try (PDDocument document = PDDocument.load(fisierMultipart.getInputStream())) {
                    PDFTextStripper stripper = new PDFTextStripper();
                    return stripper.getText(document);
                }
            }
            // Dacă încarcă o imagine, folosim Tesseract local
            else {
                File fisierTemporar = File.createTempFile("cv_upload", numeFisier);
                try (FileOutputStream fos = new FileOutputStream(fisierTemporar)) {
                    fos.write(fisierMultipart.getBytes());
                }

                Tesseract tesseract = new Tesseract();
                // Folosește calea unde ai instalat programul executabil
                tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata");
                tesseract.setLanguage("eng"); // Setat pe engleză conform CV-urilor tale

                String textExtras = tesseract.doOCR(fisierTemporar);
                fisierTemporar.delete();

                return textExtras;
            }
        } catch (Exception e) {
            System.out.println("Eroare la procesarea documentului de către Agent: " + e.getMessage());
            return "Eroare la procesarea fișierului.";
        }
    }
}