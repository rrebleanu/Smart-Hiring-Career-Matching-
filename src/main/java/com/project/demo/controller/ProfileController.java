package com.project.demo.controller;

import com.project.demo.model.Angajator;
import com.project.demo.model.CV;
import com.project.demo.model.Candidat;
import com.project.demo.repository.CVRepository;
import com.project.demo.repository.CandidatRepository;
import com.project.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class ProfileController {

    private final UserService userService;
    private final CandidatRepository candidatRepository;
    private final CVRepository cvRepository;

    public ProfileController(UserService userService, CandidatRepository candidatRepository,CVRepository cvRepository) {
        this.userService = userService;
        this.candidatRepository = candidatRepository;
        this.cvRepository = cvRepository;
    }

    @GetMapping
    public String showProfile(Model model) {
        Object user = userService.getCurrentUser();

        if (user instanceof Candidat) {
            model.addAttribute("user", user);
            return "candidat/dashboard"; // Point to a candidate-specific view
        } else if (user instanceof Angajator) {
            model.addAttribute("user", user);
            return "angajator/dashboard"; // Point to an employer-specific view
        }

        return "redirect:/login";
    }

    @PostMapping("/update")
    public String updateProfile(@ModelAttribute Candidat updatedData,
                                @RequestParam("cvFile") MultipartFile cvFile) {
        Object user = userService.getCurrentUser();

        if (user instanceof Candidat current) {
            current.setNumeUser(updatedData.getNumeUser());

            if (!cvFile.isEmpty()) {
                try {
                    List<CV> listacv=cvRepository.findByCandidat(current);
                    if (listacv == null) {
                        CV cv = new CV();

                        // Salvăm metadatele și conținutul binar
                        cv.setFileName(cvFile.getOriginalFilename());
                        cv.setFileType(cvFile.getContentType());
                        cv.setData(cvFile.getBytes()); // Aici se întâmplă "magia"
                        cv.setCandidate(current);
                        cvRepository.save(cv);
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Eroare la citirea fișierului: " + e.getMessage());
                }
            }
            candidatRepository.save(current);
        }
        return "redirect:/dashboard?success";
    }

    private String saveFile(MultipartFile file) {
        String uploadDir = "C:/uploads/"; // Use the same path as WebConfig
        try {
            Path path = Paths.get(uploadDir + System.currentTimeMillis() + "_" + file.getOriginalFilename());
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());
            return "/uploads/" + path.getFileName().toString();
        } catch (IOException e) {
            throw new RuntimeException("Upload failed", e);
        }
    }
}