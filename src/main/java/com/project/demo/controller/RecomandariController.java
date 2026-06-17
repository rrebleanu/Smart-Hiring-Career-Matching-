package com.project.demo.controller;

import com.project.demo.model.Anunt;
import com.project.demo.model.CV;
import com.project.demo.model.User;
import com.project.demo.model.Candidat;
import com.project.demo.repository.AnuntRepository;
import com.project.demo.repository.UserRepository;
import com.project.demo.service.AIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class RecomandariController {

    @Autowired
    private AIService aiService;

    @Autowired
    private AnuntRepository anuntRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/candidat/recomandari")
    public String veziTop3Recomandari(Model model, Principal principal) {
        // 1. Luăm utilizatorul logat curent
        User userLogat = userRepository.findByEmail(principal.getName());

        // Transformăm explicit User-ul în Candidat (Casting)
        Candidat candidat = (Candidat) userLogat;

        // Verificăm dacă are CV încărcat
        if (candidat.getCvs() == null || candidat.getCvs().isEmpty()) {
            return "redirect:/candidat/dashboard?eroare=FaraCV";
        }

        // 2. Extragem textul din CV-ul candidatului (luăm ultimul/primul CV salvat)
        CV cvCurent = candidat.getCvs().get(0);
        String textCV = cvCurent.getDescriereCandidat();

        // 3. Luăm toate anunțurile din baza de date
        List<Anunt> toateAnunturile = (List<Anunt>) anuntRepository.findAll();

        // 4. Folosim AI-ul ca să facă magia și să găsească TOP 3
        List<Map.Entry<Anunt, Double>> top3Joburi = aiService.gasesteTop3Joburi(textCV, toateAnunturile);

        // 5. Trimitem rezultatul către pagina HTML
        model.addAttribute("topJoburi", top3Joburi);

        return "recomandari"; // Numele paginii HTML
    }
}