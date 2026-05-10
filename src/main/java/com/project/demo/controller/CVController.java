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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/cv")
public class CVController {

    private final CVRepository cvRepository;
    private final UserService userService;

    public CVController(CVRepository cvRepository, CandidatRepository candidatRepository, UserService userService) {
        this.cvRepository = cvRepository;
        this.userService = userService;
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file,
                         RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Te rog selectează un fișier!");
            return "redirect:/cv/upload";
        }

        String contentType = file.getContentType();
        if (contentType == null ||
                (!contentType.equals("application/pdf") &&
                        !contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))) {
            redirectAttributes.addFlashAttribute("error", "Doar fișiere PDF sau DOCX sunt acceptate!");
            return "redirect:/cv/upload";
        }

        try {

            // dezactivezi toate CV-urile existente
            cvRepository.findAll().forEach(cv -> {
                cv.setActiv(false);
                cvRepository.save(cv);
            });



            CV cv = new CV();
            cv.setFileName(file.getOriginalFilename());
            cv.setFileType(file.getContentType());
            cv.setData(file.getBytes());
            cv.setCandidat((Candidat) userService.getCurrentUser());
            cv.setActiv(true);
            /*TO DO - DE FACUT STATUS ACTIV LA ACTUALUL CV SI DEZACTIVAT CEL ACTIV ANTERIOR (DACA E)*/
            cvRepository.save(cv);
            redirectAttributes.addFlashAttribute("success", "CV încărcat cu succes!");
            return "redirect:/profil";
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Eroare la salvare: " + e.getMessage());
            return "redirect:/cv/upload";
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> download(@PathVariable Integer id) {
        CV cv = cvRepository.findById(id).orElseThrow();
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(cv.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + cv.getFileName() + "\"")
                .body(cv.getData());
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<byte[]> view(@PathVariable Integer id) {
        CV cv = cvRepository.findById(id).orElseThrow();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(cv.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + cv.getFileName() + "\"")
                .body(cv.getData());
    }


    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id,
                         RedirectAttributes redirectAttributes) {
        CV cv = cvRepository.findById(id).orElseThrow();
        cvRepository.delete(cv);
        redirectAttributes.addFlashAttribute("success", "CV șters cu succes!");
        return "redirect:/profil";
    }
}
