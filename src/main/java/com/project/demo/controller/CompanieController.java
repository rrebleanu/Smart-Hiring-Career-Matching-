package com.project.demo.controller;

import com.project.demo.model.Companie;
import com.project.demo.repository.CompanieRepository;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@RestController // This means the class handles web requests and returns data directly
@RequestMapping(path="/api/companie") // URLs will start with /api/companie
public class CompanieController {

    private final CompanieRepository companieRepository;

    // Constructor injection for database access
    public CompanieController(CompanieRepository companieRepository) {
        this.companieRepository = companieRepository;
    }

    @PostMapping(path="/add") // Map POST requests for adding data
    public String addNewCompanie (@RequestBody Companie newCompanie) {
        // Spring maps the received data directly to the 'newCompanie' object
        companieRepository.save(newCompanie); // Save it to the 'companies' table
        return "Company Saved Successfully";
    }

    @GetMapping(path="/all") // Map GET requests for reading data
    public Iterable<Companie> getAllCompanii() {
        // Return a list of all companies from the database
        return companieRepository.findAll();
    }

}