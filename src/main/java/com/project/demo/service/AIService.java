package com.project.demo.service;

import com.project.demo.model.Anunt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AIService {

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    public String askGemini(String prompt) {
        // Folosim ruta exactă pentru contul tău: gemini-2.5-flash
        String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + geminiApiKey;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Curățăm textul
        String safePrompt = prompt.replace("\"", "'").replace("\n", " ");
        String requestBody = "{ \"contents\": [{ \"parts\": [{\"text\": \"" + safePrompt + "\"}] }] }";

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        try {
            return restTemplate.postForObject(apiUrl, request, String.class);
        } catch (Exception e) {
            System.out.println("Eroare severă la conexiunea cu Google: " + e.getMessage());
            return "EROARE_API";
        }
    }

    public Double calculeazaCompatibilitate(String textCV, String descriereJob) {
        // PROMPT STRICT: Îl obligăm să ne dea DOAR un număr
        String prompt = "Ești un recrutor IT expert. Analizează CV-ul și Jobul. " +
                "Evaluează compatibilitatea dintre ele. " +
                "Returnează STRICT un singur număr între 0 și 100. " +
                "Nu scrie niciun alt cuvânt, nicio propoziție și NU include simbolul procent (%). Vreau DOAR numărul. " +
                "CV: " + textCV + " | JOB: " + descriereJob;

        String raspunsBrut = askGemini(prompt);
        System.out.println("RĂSPUNS DIRECT DE LA AI: " + raspunsBrut);

        if (raspunsBrut.equals("EROARE_API")) {
            return 50.0; // Fallback dacă a picat conexiunea
        }

        try {
            // Extragem valoarea din JSON
            String cautat = "\"text\": \"";
            String textExtras = "";

            if (raspunsBrut.contains(cautat)) {
                int start = raspunsBrut.indexOf(cautat) + cautat.length();
                int end = raspunsBrut.indexOf("\"", start);
                textExtras = raspunsBrut.substring(start, end);
            } else {
                textExtras = raspunsBrut;
            }

            // CURĂȚAREA SUPREMĂ: Păstrăm doar cifrele și punctul zecimal
            String numarCurat = textExtras.replaceAll("[^0-9.]", "").trim();

            // Dacă din greșeală a șters tot, evităm crash-ul
            if (numarCurat.isEmpty()) {
                System.out.println("AI-ul nu a returnat cifre valabile.");
                return 50.0;
            }

            return Double.parseDouble(numarCurat);

        } catch (Exception e) {
            System.out.println("Nu s-a putut parsa procentul. Text brut: " + raspunsBrut);
            return 50.0;
        }
    }

    public List<Map.Entry<Anunt, Double>> gasesteTop3Joburi(String textCV, List<Anunt> toateAnunturile) {
        Map<Anunt, Double> scoruri = new HashMap<>();

        int limita = Math.min(toateAnunturile.size(), 10);
        for (int i = 0; i < limita; i++) {
            Anunt anunt = toateAnunturile.get(i);
            Double scor = calculeazaCompatibilitate(textCV, anunt.getDescriereJob());
            scoruri.put(anunt, scor);

            try {
                Thread.sleep(2000); // Pauză de 2 secunde pentru a evita limitările Google
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return scoruri.entrySet().stream()
                .sorted(Map.Entry.<Anunt, Double>comparingByValue().reversed())
                .limit(3)
                .collect(Collectors.toList());
    }
}