package com.project.demo.controller;

import com.project.demo.model.CV;
import com.project.demo.model.Candidat;
import com.project.demo.repository.CVRepository;
import com.project.demo.repository.CandidatRepository;
import com.project.demo.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cv")
public class CVController {

    private final CVRepository cvRepository;
    private final CandidatRepository candidatRepository;
    private final UserService userService;

    public CVController(CVRepository cvRepository, CandidatRepository candidatRepository, UserService userService) {
        this.cvRepository = cvRepository;
        this.candidatRepository = candidatRepository;
        this.userService = userService;
    }

    // 1. Show the "Create CV" form
    @GetMapping("/nou")
    public String arataFormularCV(Model model) {
        Object user = userService.getCurrentUser();
        if (!(user instanceof Candidat currentCandidat)) {
            return "redirect:/login"; // Only candidates can make CVs
        }

        // If the candidate already has a CV, redirect them to the edit page or dashboard
        if (currentCandidat.getCv() != null) {
            model.addAttribute("cv", currentCandidat.getCv());
        } else {
            model.addAttribute("cv", new CV());
        }

        return "candidat/creaza-cv"; // This points to templates/candidat/creaza-cv.html
    }

    // 2. Save the CV form details and link them to the logged-in Candidate
    @PostMapping("/salveaza")
    public String salveazaCV(@ModelAttribute("cv") CV cvForm) {
        Object user = userService.getCurrentUser();
        if (!(user instanceof Candidat currentCandidat)) {
            return "redirect:/login";
        }

        // Save the CV to the database
        CV savedCv = cvRepository.save(cvForm);

        // Link the saved CV to the current candidate and save the candidate
        currentCandidat.setCv(savedCv);
        candidatRepository.save(currentCandidat);

        return "redirect:/dashboard?cvSuccess";
    }
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> getCV(@PathVariable Integer id) {
        CV cv = cvRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CV-ul nu a fost găsit"));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(cv.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + cv.getFileName() + "\"")
                .body(cv.getData());
    }
}