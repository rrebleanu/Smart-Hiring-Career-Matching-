package com.project.demo.controller;

import com.project.demo.model.Anunt;
import com.project.demo.repository.AnuntRepository;
import com.project.demo.service.AnunturiService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller // Controller for handling Job Ads (Anunturi)
@RequestMapping("/anunturi")
public class AnuntController {

//    @PostMapping(path="/add") // Accepts POST requests

//    public String addNewAnunt (@RequestBody Anunt newAnunt) {
//        // Saves the new job advertisement (titlu, descriere, salariu etc.)
//
//    }

    private final AnunturiService anuntService;
    AnuntController(AnunturiService anuntService)
    {
        this.anuntService = anuntService;
    }

    @GetMapping("/adauga")
    public String formAdauga(Model model) {
        model.addAttribute("anunt", new Anunt());
        return "adauga-anunt";
    }

    @PostMapping("/adauga")
    public String salveaza(@ModelAttribute Anunt anunt) {
        anuntService.save(anunt);
        return "redirect:/anunturi";
    }


    @GetMapping
    public String listaAnunturi(Model model) {
        model.addAttribute("anunturi", anuntService.getAll());
        return "anunturi";
    }
//
//    @GetMapping(path="/all") // Accepts GET requests
//    public Iterable<Anunt> getAllAnunturi() {
//        // Retrieves all job ads from the 'anunturi' table
//
//    }
}