package com.project.demo.controller;

import com.project.demo.model.Anunt;
import com.project.demo.model.CV;
import com.project.demo.model.Candidat;
import com.project.demo.model.User;
import com.project.demo.repository.AnuntRepository;
import com.project.demo.repository.CVRepository;
import com.project.demo.service.AIService;
import com.project.demo.service.AplicareService;
import com.project.demo.service.DocumentOCRService;
import com.project.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.Boolean.TRUE;

@Controller
public class RecomandariController {

    private final AIService aiService;
    private final AnuntRepository anuntRepository;
    private final UserService userService;
    private final CVRepository cvRepository;
    private final DocumentOCRService documentOCRService;
    private final AplicareService aplicareService;

    public RecomandariController(AplicareService aplicareService, AIService aiService, AnuntRepository anuntRepository, UserService userService, CVRepository cvRepository, DocumentOCRService documentOCRService) {
        this.aiService = aiService;
        this.anuntRepository = anuntRepository;
        this.userService = userService;
        this.cvRepository = cvRepository;
        this.documentOCRService = documentOCRService;
        this.aplicareService = aplicareService;
    }

    @GetMapping("/candidat/recomandari")
    public String veziTop3Recomandari(Model model) {
        Candidat candidat = (Candidat) userService.getCurrentUser();

        // 1. Căutăm CV-ul candidatului logat
        List<CV> listaCvs = (List<CV>) cvRepository.findByActiv(TRUE);
        CV cvCurent = null;
        for (CV cv : listaCvs) {
            if (cv.getCandidate() != null && cv.getCandidate().getIdUser().equals(candidat.getIdUser())) {
                cvCurent = cv;
                break;
            }
        }

        // 2. Dacă nu are CV, redirect corect către profil
        if (cvCurent == null || cvCurent.getData() == null) {
            return "redirect:/profil?eroare=FaraCV";
        }

        // 3. Extragem textul cu OCR
        String textCV = "";
        try {
            textCV = documentOCRService.extractText(cvCurent.getId());
        } catch (Exception e) {
            System.out.println("Eroare la extragerea OCR: " + e.getMessage());
        }

        // 4. Luăm joburile și aplicăm limita pentru a nu bloca serverul
        List<Anunt> toateAnunturile = (List<Anunt>) anuntRepository.findAll();

        int limita = Math.min(toateAnunturile.size(), 15);
        List<Anunt> anunturiDeProcesat = toateAnunturile.subList(0, limita);

        Map<Anunt, Double> scoruri = new HashMap<>();

        System.out.println("Controller: Începem procesarea individuală pentru " + anunturiDeProcesat.size() + " joburi...");

        // 5. Bucla iterativă (Apeluri multiple către AI)
        for (Anunt anunt : anunturiDeProcesat) {

            // Apelăm AI-ul STRICT pentru acest job
            Double scor = aiService.calculeazaScorPentruJob(textCV, anunt);
            scoruri.put(anunt, scor);

            System.out.println("Controller: Job ID " + anunt.getId() + " analizat. Scor AI: " + scor + "%");

            try {
                // Pauză obligatorie de 0.5s între apeluri multiple pentru a nu primi "429 Too Many Requests"
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Controller: Procesare finalizată! Calculăm top 3...");

        // 6. Sortăm harta și extragem Top 3 cele mai bune joburi
        List<Map.Entry<Anunt, Double>> top3Joburi = scoruri.entrySet().stream()
                .sorted(Map.Entry.<Anunt, Double>comparingByValue().reversed())
                .limit(3)
                .collect(Collectors.toList());

        model.addAttribute("topJoburi", top3Joburi);
        User currentUser = userService.getCurrentUser();
        Set<Integer> anunturiAplicate;
        anunturiAplicate = aplicareService.anunturiAplicate((Candidat) currentUser);
        model.addAttribute("anunturiAplicate", anunturiAplicate);


        return "recomandari";
    }
}