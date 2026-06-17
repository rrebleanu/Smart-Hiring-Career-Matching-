package com.project.demo.controller;

import com.project.demo.model.CV;
import com.project.demo.model.Candidat;
import com.project.demo.repository.CVRepository;
import com.project.demo.service.OCRService;
import com.project.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(path="/candidat")
public class CandidatController {

    private final CVRepository cvRepository;
    private final OCRService ocrService;
    private final UserService userService;

    public CandidatController(CVRepository cvRepository, OCRService ocrService, UserService userService) {
        this.cvRepository = cvRepository;
        this.ocrService = ocrService;
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        Candidat candidatCurent = (Candidat) userService.getCurrentUser();
        model.addAttribute("candidat", candidatCurent);
        return "candidat/dashboard";
    }

    @PostMapping("/incarca-cv")
    public String proceseazaCV(@RequestParam("fisierCV") MultipartFile fisier) {
        Candidat candidatCurent = (Candidat) userService.getCurrentUser();
        if (candidatCurent == null) {
            return "redirect:/login";
        }

        // Agentul extrage conținutul text din fișier (PDF sau Imagine)
        String textCV = ocrService.extrageTextDinCV(fisier);

        // Salvăm în tabela cvs mapată în proiectul tău
        CV cvNou = new CV();
        cvNou.setCandidate(candidatCurent); // Folosește setterul nativ setCandidate
        cvNou.setDescriereCandidat(textCV);
        cvNou.setDomeniu("Procesat automat prin AI Agent");

        cvRepository.save(cvNou);

        return "redirect:/candidat/dashboard?succes=true";
    }
}