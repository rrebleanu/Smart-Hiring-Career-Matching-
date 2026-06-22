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
        String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + geminiApiKey;
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

    public List<Map.Entry<Anunt, Double>> gasesteTop3Joburi(String textCV, List<Anunt> toateAnunturile) {
        Map<Anunt, Double> scoruri = new HashMap<>();

        if (toateAnunturile == null || toateAnunturile.isEmpty()) {
            return new ArrayList<>();
        }

        int limita = Math.min(toateAnunturile.size(), 10);
        List<Anunt> anunturiDeProcesat = toateAnunturile.subList(0, limita);

        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("Esti un recrutor IT. Analizeaza acest CV si lista de Joburi. Evalueaza compatibilitatea (de la 0 la 100) pentru FIECARE job. ");
        promptBuilder.append("Returneaza STRICT pe un singur rand, separate prin virgula, in formatul ID:SCOR. Fara alte cuvinte, fara markdown, fara cod. Exemplu raspuns perfect: 1:85.5, 2:40.0, 3:92.1\n\n");
        promptBuilder.append("CV:\n").append(textCV).append("\n\nJOBURI:\n");

        for (Anunt anunt : anunturiDeProcesat) {
            // CORECTAT: Folosim getId() pentru ca asa se numeste in clasa ta Anunt
            promptBuilder.append("ID ").append(anunt.getId()).append(": ").append(anunt.getDescriereJob()).append("\n");
        }

        String raspunsBrut = askGemini(promptBuilder.toString());

        String textExtras = extrageTextDinJSON(raspunsBrut);
        Map<Integer, Double> idScorMap = parseazaScoruri(textExtras);

        for (Anunt anunt : anunturiDeProcesat) {
            // CORECTAT: Folosim getId()
            Double scor = idScorMap.getOrDefault(anunt.getId(), 50.0);
            scoruri.put(anunt, scor);
        }

        return scoruri.entrySet().stream()
                .sorted(Map.Entry.<Anunt, Double>comparingByValue().reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    private String extrageTextDinJSON(String json) {
        if (json.equals("EROARE_API")) return "";
        try {
            String cautat = "\"text\": \"";
            if (json.contains(cautat)) {
                int start = json.indexOf(cautat) + cautat.length();
                int end = json.indexOf("\"", start);
                String text = json.substring(start, end);
                return text.replace("\\n", " ").replace("\\", "").trim();
            }
        } catch (Exception e) {
            System.out.println("Eroare la extragere text JSON: " + e.getMessage());
        }
        return json;
    }

    private Map<Integer, Double> parseazaScoruri(String textExtras) {
        Map<Integer, Double> idScorMap = new HashMap<>();
        try {
            String[] perechi = textExtras.split(",");
            for (String pereche : perechi) {
                String[] parti = pereche.split(":");
                if (parti.length == 2) {
                    Integer id = Integer.parseInt(parti[0].replaceAll("[^0-9]", ""));
                    Double scor = Double.parseDouble(parti[1].replaceAll("[^0-9.]", ""));
                    idScorMap.put(id, scor);
                }
            }
        } catch (Exception e) {
            System.out.println("Eroare la parsarea scorurilor din AI: " + e.getMessage());
        }
        return idScorMap;
    }
}