package com.project.demo.controller;

import com.project.demo.model.Anunt;
import com.project.demo.repository.AnuntRepository;
import org.springframework.web.bind.annotation.*;

@RestController // Controller for handling Job Ads (Anunturi)
@RequestMapping(path="/api/anunt") // Base URL
public class AnuntController {

    private final AnuntRepository anuntRepository;

    // Inject the Anunt repository
    public AnuntController(AnuntRepository anuntRepository) {
        this.anuntRepository = anuntRepository;
    }

    @PostMapping(path="/add") // Accepts POST requests
    public String addNewAnunt (@RequestBody Anunt newAnunt) {
        // Saves the new job advertisement (titlu, descriere, salariu etc.)
        anuntRepository.save(newAnunt);
        return "Job Advertisement Saved Successfully";
    }

    @GetMapping(path="/all") // Accepts GET requests
    public Iterable<Anunt> getAllAnunturi() {
        // Retrieves all job ads from the 'anunturi' table
        return anuntRepository.findAll();
    }
}