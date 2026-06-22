package com.project.demo.service;

import com.project.demo.model.Anunt;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AIService {

    private final ChatClient chatClient;

    public AIService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    // Această metodă analizează UN SINGUR job în raport cu CV-ul și returnează doar nota (procentul)
    public Double calculeazaScorPentruJob(String textCV, Anunt anunt) {

        if (textCV.length() > 12000) {
            textCV = textCV.substring(0, 12000);
        }

        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("Ești un expert în recrutare tehnică (HR). Analizează CV-ul și Jobul de mai jos.\n");
        promptBuilder.append("Evaluează compatibilitatea (de la 0.0 la 100.0) între CV și acest Job.\n");
        promptBuilder.append("REGULĂ STRICTĂ: Returnează STRICT un singur număr reprezentând procentul.\n");
        promptBuilder.append("FĂRĂ salutări, FĂRĂ explicații, FĂRĂ simbolul %. DOAR numărul (ex: 85.5).\n\n");

        promptBuilder.append("--- CV CANDIDAT ---\n").append(textCV).append("\n\n");
        promptBuilder.append("--- DESCRIERE JOB ---\n").append(anunt.getDescriereJob()).append("\n");

        try {
            String raspunsBrut = chatClient.prompt()
                    .user(promptBuilder.toString())
                    .call()
                    .content();

            return extrageScorSingular(raspunsBrut);
        } catch (Exception e) {
            System.out.println("Eroare la apelul AI pentru Jobul " + anunt.getId() + ": " + e.getMessage());
            return 0.0;
        }
    }

    private Double extrageScorSingular(String textExtras) {
        if (textExtras == null || textExtras.trim().isEmpty()) {
            return 0.0;
        }

        try {
            // Caută primul număr din răspunsul AI-ului (întreg sau cu zecimale)
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("(\\d+(\\.\\d+)?)");
            java.util.regex.Matcher matcher = pattern.matcher(textExtras);
            if (matcher.find()) {
                return Double.parseDouble(matcher.group(1));
            }
        } catch (Exception e) {
            System.out.println("Eroare la parsarea scorului: " + e.getMessage() + " | Răspuns: " + textExtras);
        }

        return 0.0;
    }
}