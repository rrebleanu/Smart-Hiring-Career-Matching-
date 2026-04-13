package com.project.demo.controller;

import com.project.demo.model.CV;
import com.project.demo.repository.CVRepository;
import org.springframework.web.bind.annotation.*;

@RestController // Handles HTTP requests for CVs
@RequestMapping(path="/api/cv")
public class CVController {

    private final CVRepository cvRepository;

    // Constructor injection
    public CVController(CVRepository cvRepository) {
        this.cvRepository = cvRepository;
    }

    @PostMapping(path="/add")
    public String addNewCV (@RequestBody CV newCV) {
        // Persists the CV details (telefon, experienta, domeniu)
        cvRepository.save(newCV);
        return "CV Saved Successfully";
    }

    @GetMapping(path="/all")
    public Iterable<CV> getAllCVs() {
        // Returns the list of all CVs
        return cvRepository.findAll();
    }
}