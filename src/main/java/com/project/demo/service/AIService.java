package com.project.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AIService {

    // Aici Spring Boot va "injecta" automat cheia din application.properties
    @Value("${gemini.api.key}")
    private String geminiApiKey;

    public String askGemini(String prompt) {
        // Link-ul oficial către modelul gratuit și rapid Gemini 1.5 Flash
        String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + geminiApiKey;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Curățăm textul pentru a nu strica formatul JSON manual
        String safePrompt = prompt.replace("\"", "'").replace("\n", " ");

        // Acesta este formatul exact de pachet (JSON) pe care îl cere Google
        String requestBody = "{ \"contents\": [{ \"parts\": [{\"text\": \"" + safePrompt + "\"}] }] }";

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        try {
            // Trimitem pachetul către Google și primim răspunsul
            return restTemplate.postForObject(apiUrl, request, String.class);
        } catch (Exception e) {
            System.out.println("Eroare AI: " + e.getMessage());
            return "Eroare la comunicarea cu AI-ul.";
        }
    }

    public Double calculeazaCompatibilitate(String textCV, String descriereJob) {
        String prompt = "Ești un recrutor IT expert. Analizează următorul CV și următoarea descriere a jobului. " +
                "Calculează un procent de compatibilitate între 0.0 și 100.0 bazat pe abilități, tehnologii și experiență. " +
                "Răspunde DOAR cu numărul zecimal (fără text, fără simbolul %, fără explicații). " +
                "CV: " + textCV + " | JOB: " + descriereJob;

        String raspunsBrut = askGemini(prompt);

        try {
            // Extragere simplă și rapidă a textului din structura JSON primită de la Google Gemini
            // Evită adăugarea unei alte librării greoaie de parsing JSON
            String cautat = "\"text\": \"";
            if (raspunsBrut.contains(cautat)) {
                int start = raspunsBrut.indexOf(cautat) + cautat.length();
                int end = raspunsBrut.indexOf("\"", start);
                String textNumar = raspunsBrut.substring(start, end).trim();

                // Curățăm textul extras de orice altceva în afară de cifre și punct
                String numarCurat = textNumar.replaceAll("[^0-9.]", "");
                return Double.parseDouble(numarCurat);
            }

            String numarCurat = raspunsBrut.replaceAll("[^0-9.]", "").trim();
            return Double.parseDouble(numarCurat);
        } catch (Exception e) {
            System.out.println("Nu s-a putut parsa procentul de la AI, folosim fallback de siguranță.");
            return 50.0; // Valoare de siguranță în caz că structura sau formatul diferă
        }
    }
}