package com.project.demo.controller;

import com.project.demo.model.Companie;
import com.project.demo.repository.CompanieRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.List;

@Controller // This means the class handles web requests and returns data directly
@RequestMapping(path="/companii") // URLs will start with /api/companie
public class CompanieController {

    private final CompanieRepository companieRepository;

    // Constructor injection for database access
    public CompanieController(CompanieRepository companieRepository) {
        this.companieRepository = companieRepository;
    }



    @GetMapping // Map GET requests for reading data
    public String getAllCompanii(Model model) {
        // Return a list of all companies from the database
        List<Companie> companii =  (List<Companie>) companieRepository.findAll();
        model.addAttribute("companii", companii);
        return "companii";
    }

}