//package com.project.demo.controller;
//
//import com.project.demo.model.Aplicare;
//import com.project.demo.model.Candidat;
//import com.project.demo.model.Anunt;
//import com.project.demo.repository.AplicareRepository;
//import com.project.demo.repository.AnuntRepository;
//import com.project.demo.service.AIService;
//import com.project.demo.service.UserService;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//@RequestMapping(path="/aplica")
//public class AplicareController {
//
//    private final AplicareRepository aplicareRepository;
//    private final AnuntRepository anuntRepository;
//    private final UserService userService;
//    private final AIService aiService;
//
//    public AplicareController(AplicareRepository aplicareRepository,
//                              AnuntRepository anuntRepository,
//                              UserService userService,
//                              AIService aiService) {
//        this.aplicareRepository = aplicareRepository;
//        this.anuntRepository = anuntRepository;
//        this.userService = userService;
//        this.aiService = aiService;
//    }
//
//    @GetMapping("/{idAnunt}")
//    public String aplicaLaJob(@PathVariable("idAnunt") Integer idAnunt) {
//        // 1. Obținem candidatul logat curent
//        Candidat candidatCurent = (Candidat) userService.getCurrentUser();
//
//        // 2. Găsim anunțul la care se aplică
//        Anunt anunt = anuntRepository.findById(idAnunt).orElse(null);
//
//        if (anunt == null || candidatCurent == null) {
//            return "redirect:/anunturi";
//        }
//
//        // 3. Pregătim datele pentru AI folosind câmpurile native ale proiectului tău
//        String textCV = "Candidat ID: " + candidatCurent.getIdUser() + ". ";
//        if (candidatCurent.getEmail() != null) {
//            textCV += "Email: " + candidatCurent.getEmail() + ". ";
//        }
//
//        // Construim descrierea completă a jobului din baza ta de date
//        String textJob = "Titlu: " + anunt.getTitlu() +
//                " | Descriere: " + anunt.getDescriereJob() +
//                " | Domeniu: " + anunt.getDomeniuJob();
//
//        // 4. Chemăm Agentul AI să calculeze compatibilitatea (returnează un Double)
//        Double procentCompatibilitate = aiService.calculeazaCompatibilitate(textCV, textJob);
//
//        // 5. Salvăm aplicarea în baza de date cu valoarea calculată de Gemini
//        Aplicare aplicareNoua = new Aplicare();
//        aplicareNoua.setCandidat(candidatCurent);
//        aplicareNoua.setAnunt(anunt);
//
//        // Se mapează direct pe coloana 'probabilitate_compatibilitate' din baza de date
//        aplicareNoua.setProbabilitateCompatibilitate(procentCompatibilitate);
//
//        aplicareRepository.save(aplicareNoua);
//
//        return "redirect:/anunturi?succesAplicare=true";
//    }
//}