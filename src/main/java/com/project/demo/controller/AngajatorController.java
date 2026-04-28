package com.project.demo.controller;

import com.project.demo.model.Angajator;
import com.project.demo.repository.AngajatorRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

@RestController // Controller for Employers
@RequestMapping(path="/api/angajator")
public class AngajatorController {

    private final AngajatorRepository angajatorRepository;

    // Constructor injection
    public AngajatorController(AngajatorRepository angajatorRepository) {
        this.angajatorRepository = angajatorRepository;
    }

    @PostMapping(path="/add")
    public String addNewAngajator (@RequestBody Angajator newAngajator) {
        // Saves the employer entity to the database
        angajatorRepository.save(newAngajator);
        return "Employer Saved Successfully";
    }

    @GetMapping(path="/all")
    public Iterable<Angajator> getAllAngajatori() {
        return angajatorRepository.findAll();
    }

    @GetMapping("/jobs")
    public String showJobs(HttpSession session) {
        if (session.getAttribute("userLogat") == null) {
            return "redirect:/login"; // Kick them back to login
        }
        return "jobs";
    }
}