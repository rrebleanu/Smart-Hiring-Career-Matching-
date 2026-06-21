package com.project.demo.controller;

import com.project.demo.model.Anunt;
import com.project.demo.model.CV;
import com.project.demo.model.Candidat;
import com.project.demo.repository.AnuntRepository;
import com.project.demo.repository.CVRepository;
import com.project.demo.service.AIService;
import com.project.demo.service.DocumentOCRService;
import com.project.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class RecomandariController {

    private final AIService aiService;
    private final AnuntRepository anuntRepository;
    private final UserService userService;
    private final CVRepository cvRepository;
    private final DocumentOCRService documentOCRService;

    public RecomandariController(AIService aiService, AnuntRepository anuntRepository, UserService userService, CVRepository cvRepository, DocumentOCRService documentOCRService) {
        this.aiService = aiService;
        this.anuntRepository = anuntRepository;
        this.userService = userService;
        this.cvRepository = cvRepository;
        this.documentOCRService = documentOCRService;
    }

    @GetMapping("/candidat/recomandari")
    public String veziTop3Recomandari(Model model) {
        Candidat candidat = (Candidat) userService.getCurrentUser();

        // Căutăm CV-ul candidatului
        List<CV> listaCvs = (List<CV>) cvRepository.findAll();
        CV cvCurent = null;
        for (CV cv : listaCvs) {
            if (cv.getCandidate() != null && cv.getCandidate().getIdUser().equals(candidat.getIdUser())) {
                cvCurent = cv;
                break;
            }
        }

        if (cvCurent == null || cvCurent.getData() == null) {
            return "redirect:/candidat/profil?eroare=FaraCV";
        }

        // Folosim direct DocumentOCRService-ul colegului dându-i doar ID-ul!
        String textCV = "";
        try {
            textCV = documentOCRService.extractText(cvCurent.getId());
        } catch (Exception e) {
            System.out.println("Eroare la OCR: " + e.getMessage());
        }

        List<Anunt> toateAnunturile = (List<Anunt>) anuntRepository.findAll();
        List<Map.Entry<Anunt, Double>> top3Joburi = aiService.gasesteTop3Joburi(textCV, toateAnunturile);

        model.addAttribute("topJoburi", top3Joburi);
        return "recomandari";
    }
}