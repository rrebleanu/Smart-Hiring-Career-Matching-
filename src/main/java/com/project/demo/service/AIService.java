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
//        String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + geminiApiKey;
//        String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + geminiApiKey;
        String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-pro:generateContent?key=" + geminiApiKey;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String safePrompt = prompt.replace("\"", "'").replace("\n", " ");
        String requestBody = "{ \"contents\": [{ \"parts\": [{\"text\": \"" + safePrompt + "\"}] }] }";

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        try {
            return restTemplate.postForObject(apiUrl, request, String.class);
        } catch (Exception e) {
            System.out.println("Eroare API: " + e.getMessage());
            return "EROARE_API";
        }
    }

    public Double calculeazaCompatibilitate(String textCV, String descriereJob) {
        String prompt = "Ești un recrutor IT. Analizează CV-ul și Jobul. Returnează STRICT un singur număr între 0 și 100. Nu scrie alt cuvânt și NU include simbolul %. Vreau DOAR numărul. CV: " + textCV + " | JOB: " + descriereJob;
        String raspunsBrut = askGemini(prompt);

        if (raspunsBrut.equals("EROARE_API")) return 50.0;

        try {
            String cautat = "\"text\": \"";
            String textExtras = raspunsBrut.contains(cautat) ?
                    raspunsBrut.substring(raspunsBrut.indexOf(cautat) + cautat.length(), raspunsBrut.indexOf("\"", raspunsBrut.indexOf(cautat) + cautat.length())) : raspunsBrut;

            String numarCurat = textExtras.replaceAll("[^0-9.]", "").trim();
            return numarCurat.isEmpty() ? 50.0 : Double.parseDouble(numarCurat);
        } catch (Exception e) {
            return 50.0;
        }
    }

    public List<Map.Entry<Anunt, Double>> gasesteTop3Joburi(String textCV, List<Anunt> toateAnunturile) {
        Map<Anunt, Double> scoruri = new HashMap<>();
        int limita = Math.min(toateAnunturile.size(), 10);

        // Caută această secțiune și pune 4000 în loc de 2000:
        for (int i = 0; i < limita; i++) {
            Anunt anunt = toateAnunturile.get(i);
            scoruri.put(anunt, calculeazaCompatibilitate(textCV, anunt.getDescriereJob()));

            // Mărim pauza la 4 secunde (4000 ms) pentru a nu supăra serverele Google
            try { Thread.sleep(4000); } catch (InterruptedException ignored) {}
        }

        return scoruri.entrySet().stream()
                .sorted(Map.Entry.<Anunt, Double>comparingByValue().reversed())
                .limit(3)
                .collect(Collectors.toList());
    }
}