

package com.project.demo.service;
import com.project.demo.model.CV;
import com.project.demo.repository.CVRepository;
import net.sourceforge.tess4j.Tesseract;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.nio.file.Paths;

@Service
public class OcrService {


    private final CVRepository repository;
    private final Tesseract tesseract;


    public OcrService(
            CVRepository repository,
            @Value("${ocr.tessdata.path}") String path
    ) {

        this.repository = repository;

        tesseract = new Tesseract();

        tesseract.setDatapath(
                Paths.get(path)
                        .toAbsolutePath()
                        .toString()
        );

        tesseract.setLanguage("ron+eng");
    }



    public String extractText(int id)
            throws Exception {


        CV doc =
                repository.findById(id)
                        .orElseThrow();


        byte[] pdfBytes = doc.getData();


        try(PDDocument pdf =
                    Loader.loadPDF(pdfBytes)) {


            // 1. încercăm text normal

            PDFTextStripper stripper =
                    new PDFTextStripper();


            String text =
                    stripper.getText(pdf);


            if(text.length() > 100) {

                return text;
            }



            // 2. OCR

            PDFRenderer renderer =
                    new PDFRenderer(pdf);


            StringBuilder result =
                    new StringBuilder();



            for(int page = 0;
                page < pdf.getNumberOfPages();
                page++) {


                BufferedImage image =
                        renderer.renderImageWithDPI(
                                page,
                                300
                        );


                String ocr =
                        tesseract.doOCR(image);


                result.append(ocr)
                        .append("\n");

            }


            return result.toString();
        }
    }
}