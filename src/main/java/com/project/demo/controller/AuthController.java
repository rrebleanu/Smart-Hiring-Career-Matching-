package com.project.demo.controller;

import com.project.demo.model.Angajator;
import com.project.demo.model.Candidat;
import com.project.demo.repository.AngajatorRepository;
import com.project.demo.repository.CandidatRepository;
import com.project.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class AuthController{

    private final UserRepository userRepository;
    private final CandidatRepository candidatRepository;
    private final AngajatorRepository angajatorRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository,
                                  CandidatRepository candidatRepository,
                                  AngajatorRepository angajatorRepository,
                                  PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.candidatRepository = candidatRepository;
        this.angajatorRepository = angajatorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "log-reg/login";
    }

    // POST-ul de login e gestionat automat de Spring Security
    // Nu mai ai nevoie de @PostMapping("/login")

    // ==================== REGISTER ====================

    @GetMapping("/register")
    public String showRegisterPage() {
        return "log-reg/register"; // pagină unde userul alege tipul contului
    }

    @GetMapping("/register/candidat")
    public String showRegisterCandidatPage() {
        return "log-reg/register-candidat";
    }

    @GetMapping("/register/angajator")
    public String showRegisterAngajatorPage() {
        return "log-reg/register-angajator";
    }

    // ==================== REGISTER CANDIDAT ====================

    @PostMapping("/register/candidat")
    public String registerCandidat(@RequestParam String nume,
                                   @RequestParam String email,
                                   @RequestParam String parola,
                                   Model model) {

        if (userRepository.findByEmail(email) != null) {
            model.addAttribute("error", "Acest email este deja utilizat!");
            return "log-reg/register-candidat";
        }

        Candidat candidat = new Candidat(); // ROLE_CANDIDAT setat în constructor
        candidat.setNumeUser(nume);
        candidat.setEmail(email);
        candidat.setParola(passwordEncoder.encode(parola));
        candidat.setDataInfiintare(LocalDate.now());
        candidatRepository.save(candidat);

        return "redirect:/login?success=true";
    }

    // ==================== REGISTER ANGAJATOR ====================

    @PostMapping("/register/angajator")
    public String registerAngajator(@RequestParam String nume,
                                    @RequestParam String email,
                                    @RequestParam String parola,
                                    @RequestParam String companie,
                                    Model model) {

        if (userRepository.findByEmail(email) != null) {
            model.addAttribute("error", "Acest email este deja utilizat!");
            return "log-reg/register-angajator";
        }

        Angajator angajator = new Angajator(); // ROLE_ANGAJATOR setat în constructor
        angajator.setNumeUser(nume);
        angajator.setEmail(email);
        angajator.setNumeCompanie(companie);
        angajator.setParola(passwordEncoder.encode(parola));
        angajator.setDataInfiintare(LocalDate.now());
        angajatorRepository.save(angajator);

        return "redirect:/login?success=true";
    }

    // ==================== LOGOUT ====================

}