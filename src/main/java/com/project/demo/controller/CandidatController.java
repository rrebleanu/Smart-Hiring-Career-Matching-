package com.project.demo.controller;

import com.project.demo.model.Candidat;
import com.project.demo.repository.CandidatRepository;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@RestController // Controller for Candidates
@RequestMapping(path="/api/candidat")
public class CandidatController {

    private final CandidatRepository candidatRepository;

    // Constructor injection
    public CandidatController(CandidatRepository candidatRepository) {
        this.candidatRepository = candidatRepository;
    }

    @PostMapping(path="/add")
    public String addNewCandidat (@RequestBody Candidat newCandidat) {
        // Saves the candidate entity
        candidatRepository.save(newCandidat);
        return "Candidate Saved Successfully";
    }

    @GetMapping(path="/all")
    public Iterable<Candidat> getAllCandidati() {
        return candidatRepository.findAll();
    }

    @GetMapping("/jobs")
    public String showJobs(HttpSession session) {
        if (session.getAttribute("userLogat") == null) {
            return "redirect:/login"; // Kick them back to login
        }
        return "jobs";
    }
}