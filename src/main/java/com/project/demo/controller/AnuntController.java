package com.project.demo.controller;

import com.project.demo.model.Anunt;
import com.project.demo.model.Candidat;
import com.project.demo.model.User;
import com.project.demo.repository.AnuntRepository;
import com.project.demo.service.AnunturiService;
import com.project.demo.service.AplicareService;
import com.project.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller // Controller for handling Job Ads (Anunturi)
@RequestMapping("/anunturi")
public class AnuntController {

//    @PostMapping(path="/add") // Accepts POST requests
//
//    public String addNewAnunt (@RequestBody Anunt newAnunt) {
//        // Saves the new job advertisement (titlu, descriere, salariu etc.)
//
//    }

    private final AnunturiService anuntService;
    private final UserService userService;
    private final AplicareService aplicareService;
    AnuntController(AnunturiService anuntService, UserService userService, AplicareService aplicareService)
    {
        this.anuntService = anuntService;
        this.userService = userService;
        this.aplicareService = aplicareService;
    }
//
//    @GetMapping("/adauga")
//    public String formAdauga(Model model) {
//        model.addAttribute("anunt", new Anunt());
//        return "angajator/adauga-anunt";
//    }
//
//    @PostMapping("/adauga")
//    public String salveaza(@ModelAttribute Anunt anunt) {
//        anuntService.save(anunt);
//        return "redirect:/anunturi";
//    }


    @GetMapping
    public String listaAnunturi(Model model) {
        model.addAttribute("anunturi", anuntService.getAll());
        User currentUser = userService.getCurrentUser();
        Set<Integer> anunturiAplicate;
        if(currentUser instanceof Candidat user){
            anunturiAplicate = aplicareService.anunturiAplicate(user);
            model.addAttribute("anunturiAplicate", anunturiAplicate);
        }
        return "anunturi";
    }
//
//    @GetMapping(path="/all") // Accepts GET requests
//    public Iterable<Anunt> getAllAnunturi() {
//        // Retrieves all job ads from the 'anunturi' table
//
//    }
}