package com.project.demo.controller;

import com.project.demo.model.*;
import com.project.demo.repository.AplicareRepository;
import com.project.demo.repository.CVRepository;
import com.project.demo.repository.CandidatRepository;
import com.project.demo.service.AnunturiService;
import com.project.demo.service.AplicareService;

import com.project.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
//import jakarta.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;

@Controller // Controller for Candidates
@RequestMapping(path="/candidat")
public class CandidatController {

    private final CandidatRepository candidatRepository;
    private final UserService userService;
    private final AplicareService aplicareService;
    private final AnunturiService anunturiService;


    public CandidatController( CandidatRepository candidatRepository, UserService userService, AplicareService aplicareService, AnunturiService anunturiService) {
        this.candidatRepository = candidatRepository;
        this.aplicareService = aplicareService;
        this.userService = userService;
        this.anunturiService = anunturiService;

    }

//    @PostMapping(path="/add")
//    public String addNewCandidat (@RequestBody Candidat newCandidat) {
//        // Saves the candidate entity
//        candidatRepository.save(newCandidat);
//        return "Candidate Saved Successfully";
//    }

//    @GetMapping(path="/all")
//    public Iterable<Candidat> getAllCandidati() {
//        return candidatRepository.findAll();
//    }
//
     @PostMapping(path="/aplica/{id}")
     public String aplica(@PathVariable Integer id) {
         Anunt anunt = anunturiService.getById(id);
         Candidat currentUser = (Candidat) userService.getCurrentUser();
         aplicareService.aplica(currentUser, anunt);
         return "redirect:/anunturi";
     }


    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        Candidat candidatCurent = (Candidat) userService.getCurrentUser();
        model.addAttribute("candidat", candidatCurent);
        return "candidat/dashboard";
    }

}