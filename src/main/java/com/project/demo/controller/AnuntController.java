package com.project.demo.controller;

import com.project.demo.model.Anunt;
import com.project.demo.repository.AnuntRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller // Controller for handling Job Ads (Anunturi)
@RequestMapping(path="/anunturi") // Base URL
public class AnuntController {

//    @PostMapping(path="/add") // Accepts POST requests
//
//    public String addNewAnunt (@RequestBody Anunt newAnunt) {
//        // Saves the new job advertisement (titlu, descriere, salariu etc.)
//
//    }
//
//    @GetMapping(path="/all") // Accepts GET requests
//    public Iterable<Anunt> getAllAnunturi() {
//        // Retrieves all job ads from the 'anunturi' table
//
//    }
}