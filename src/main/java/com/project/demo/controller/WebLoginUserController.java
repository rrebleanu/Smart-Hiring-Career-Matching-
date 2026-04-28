package com.project.demo.controller;

import com.project.demo.model.User;
import com.project.demo.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class WebLoginUserController {

    private final UserRepository userRepository;

    public WebLoginUserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Afișează pagina de Login
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    // Logica de Autentificare (Login)
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String parola,
                        HttpSession session,
                        Model model) {

        User user = userRepository.findByEmail(email);

        if (user != null && user.getParola().equals(parola)) {
            // Salvăm user-ul logat în sesiune
            session.setAttribute("userLogat", user);
            return "redirect:/";
        } else {
            model.addAttribute("error", "Email sau parolă incorectă!");
            return "login";
        }
    }

    // Afișează pagina de Register
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    // Logica de Înregistrare (Register)
    @PostMapping("/register")
    public String register(@RequestParam String nume,
                           @RequestParam String email,
                           @RequestParam String parola,
                           Model model) {

        if (userRepository.findByEmail(email) != null) {
            model.addAttribute("error", "Acest email este deja utilizat!");
            return "register";
        }

        User newUser = new User();
        newUser.setNumeUser(nume);
        newUser.setEmail(email);
        newUser.setParola(parola);
        newUser.setDataInfiintare(LocalDate.now());

        userRepository.save(newUser);
        return "redirect:/login?success=true";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}