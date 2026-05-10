package com.project.demo.controller;

import com.project.demo.model.Angajator;
import com.project.demo.model.Anunt;
import com.project.demo.model.User;
import com.project.demo.repository.AngajatorRepository;
import com.project.demo.service.AnunturiService;
import com.project.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller // Controller for Employers
@RequestMapping(path="/angajator")
public class AngajatorController {

    private final AnunturiService anunturiService;
    private final UserService userService;
    public AngajatorController(AngajatorRepository angajatorRepository, AnunturiService anunturiService, UserService userService) {
        this.anunturiService = anunturiService;
        this.userService = userService;
    }


//    @GetMapping(path="/all")
//    public Iterable<Angajator> getAllAngajatori() {
//        return angajatorRepository.findAll();
//    }

          @GetMapping("/anunturi")
          public String getAnunturi(Model model){
              Angajator currentUser = (Angajator) userService.getCurrentUser();
              model.addAttribute("anunturi", anunturiService.AngajatorAnunturi(currentUser));
              return "angajator/anunturile-mele";
          }
        @GetMapping("/anunturi/adauga")
        public String formAdauga(Model model) {
            model.addAttribute("anunt", new Anunt());
            return "angajator/adauga-anunt";
        }

        @PostMapping("/anunturi/adauga")
        public String salveaza(@ModelAttribute Anunt anunt) {
            Angajator currentUser = (Angajator) userService.getCurrentUser();
            anunt.setAngajator(currentUser);
            anunturiService.save(anunt);
            return "redirect:/angajator/anunturi";
        }


    // Rute pentru functionalitatea de stergere anunt

    @GetMapping("/anunturi/sterge/{id}")
    public String stergeAnunt(@PathVariable("id") Integer id) {
        anunturiService.deleteAnuntById(id);
        return "redirect:/angajator/anunturi";
    }

    // Rute pentru functionalitatea de modificare anunt

    @GetMapping("/anunturi/modifica/{id}")
    public String formModificaAnunt(@PathVariable("id") Integer id, Model model) {
        Anunt anuntDeModificat = anunturiService.getAnuntById(id);

        if (anuntDeModificat == null) {
            return "redirect:/angajator/anunturi";
        }

        model.addAttribute("anunt", anuntDeModificat);
        return "angajator/modifica-anunt";
    }

    @PostMapping("/anunturi/modifica/{id}")
    public String salveazaModificarea(@PathVariable("id") Integer id, @ModelAttribute Anunt anuntModificat) {
        Angajator currentUser = (Angajator) userService.getCurrentUser();

        anuntModificat.setAngajator(currentUser);
        anuntModificat.setIdAnunt(id);

        anunturiService.save(anuntModificat);
        return "redirect:/angajator/anunturi";
    }
    }