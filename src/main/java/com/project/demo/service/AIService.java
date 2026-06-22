package com.project.demo.service;

import com.project.demo.model.Anunt;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AIService {

    private final ChatClient chatClient;

    public AIService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public List<Map.Entry<Anunt, Double>> gasesteTop3Joburi(String textCV, List<Anunt> toateAnunturile) {
        Map<Anunt, Double> scoruri = new HashMap<>();

        if (toateAnunturile == null || toateAnunturile.isEmpty()) {
            return new ArrayList<>();
        }

        // Procesăm maximum 15 anunțuri simultan pentru a păstra acuratețea modelului
        int limita = Math.min(toateAnunturile.size(), 15);
        List<Anunt> anunturiDeProcesat = toateAnunturile.subList(0, limita);

        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("Ești un expert în recrutare tehnică (HR). Analizează CV-ul și Joburile de mai jos.\n");
        promptBuilder.append("Evaluează compatibilitatea (de la 0.0 la 100.0) pentru FIECARE job în raport cu abilitățile din CV.\n");
        promptBuilder.append("REGULĂ STRICTĂ: Returnează DOAR un rând de text cu perechi ID:SCOR separate prin virgulă.\n");
        promptBuilder.append("FĂRĂ salutări, FĂRĂ explicații, FĂRĂ formatare markdown. Exemplu perfect: 1:85.5, 2:40.0, 3:92.1\n\n");

        promptBuilder.append("--- CV CANDIDAT ---\n").append(textCV).append("\n\n");
        promptBuilder.append("--- LISTA JOBURI ---\n");

        for (Anunt anunt : anunturiDeProcesat) {
            promptBuilder.append("ID ").append(anunt.getId()).append(": ").append(anunt.getDescriereJob()).append("\n");
        }
        System.out.println("DEBUG CHEIE: " + System.getenv("OPENAI_API_KEY"));
        String raspunsBrut;
        try {
            raspunsBrut = chatClient.prompt()
                    .user(promptBuilder.toString())
                    .call()
                    .content();
        } catch (Exception e) {
            System.out.println("Eroare la apelul AI (Agent 2 Groq): " + e.getMessage());
            raspunsBrut = "";
        }

        Map<Integer, Double> idScorMap = parseazaScoruri(raspunsBrut);

        for (Anunt anunt : anunturiDeProcesat) {
            Double scor = idScorMap.getOrDefault(anunt.getId(), 0.0);
            scoruri.put(anunt, scor);
        }

        return scoruri.entrySet().stream()
                .sorted(Map.Entry.<Anunt, Double>comparingByValue().reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    private Map<Integer, Double> parseazaScoruri(String textExtras) {
        Map<Integer, Double> idScorMap = new HashMap<>();

        if (textExtras == null || textExtras.trim().isEmpty()) {
            return idScorMap;
        }

        try {
            // Folosim Expresii Regulate (Regex) pentru a gasi STRICT cifrele din textul returnat de AI
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("(\\d+)\\s*:\\s*(\\d+(\\.\\d+)?)");
            java.util.regex.Matcher matcher = pattern.matcher(textExtras);

            while (matcher.find()) {
                Integer id = Integer.parseInt(matcher.group(1));
                Double scor = Double.parseDouble(matcher.group(2));
                idScorMap.put(id, scor);
            }
        } catch (Exception e) {
            System.out.println("Eroare la parsarea scorurilor AI: " + e.getMessage() + " | Răspuns: " + textExtras);
        }

        return idScorMap;
    }
}