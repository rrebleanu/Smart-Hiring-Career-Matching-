package com.project.demo.controller;

import com.project.demo.model.Aplicare;
import com.project.demo.repository.AplicareRepository;
import org.springframework.web.bind.annotation.*;

@RestController // Controller for job applications
@RequestMapping(path="/api/aplicare")
public class AplicareController {

    private final AplicareRepository aplicareRepository;

    // Constructor injection
    public AplicareController(AplicareRepository aplicareRepository) {
        this.aplicareRepository = aplicareRepository;
    }

    @PostMapping(path="/add")
    public String addNewAplicare (@RequestBody Aplicare newAplicare) {
        // Saves the application
        aplicareRepository.save(newAplicare);
        return "Application Saved Successfully";
    }

    @GetMapping(path="/all")
    public Iterable<Aplicare> getAllAplicari() {
        // Gets all applications
        return aplicareRepository.findAll();
    }
}