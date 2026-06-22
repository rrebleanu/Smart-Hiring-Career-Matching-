package com.project.demo.controller;

import com.project.demo.model.*;
import com.project.demo.repository.AngajatorRepository;
import com.project.demo.repository.CVRepository;
import com.project.demo.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller // Controller for Employers
@RequestMapping(path="/angajator")
public class AngajatorController {

    private final AnunturiService anunturiService;
    private final UserService userService;
    private final AplicareService aplicareService;
    private final DocumentOCRService ocrService;
    private final CVRepository cvRepository;
    private final AgentAngajatorService agentAngajatorService;
    public AngajatorController(CVRepository cvRepository, AngajatorRepository angajatorRepository, AnunturiService anunturiService, UserService userService, AplicareService aplicareService,DocumentOCRService ocrService, AgentAngajatorService agentAngajatorService ) {
        this.anunturiService = anunturiService;
        this.userService = userService;
        this.aplicareService = aplicareService;
        this.ocrService = ocrService;
        this.agentAngajatorService = agentAngajatorService;
        this.cvRepository = cvRepository;
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

            @GetMapping("/anunturi/{id}")
            public String getAplicari(@PathVariable Integer id, Model model){
            Anunt anunt = anunturiService.getById(id);
            Angajator currentUser = (Angajator) userService.getCurrentUser();
            List<Anunt> anunturi = anunturiService.AngajatorAnunturi(currentUser);
            if(anunturi.contains(anunt)) {
                List<Candidat> aplicari = aplicareService.candidati(anunt);
                model.addAttribute("aplicari", aplicari);
                model.addAttribute("id", id);
                return "angajator/aplicari";
            }
            return "redirect:/angajator/anunturi";
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
        anuntModificat.setId(id);

        anunturiService.save(anuntModificat);
        return "redirect:/angajator/anunturi";
    }


    @GetMapping("/anunturi/agent/{id}")
    public String selectareAplicariAI(@PathVariable Integer id, Model model) throws Exception {
        Anunt anunt = anunturiService.getById(id);
        Angajator currentUser = (Angajator) userService.getCurrentUser();
        List<Anunt> anunturi = anunturiService.AngajatorAnunturi(currentUser);
        if(anunturi.contains(anunt)) {
            List<Candidat> aplicari = aplicareService.candidati(anunt);
            Map<String, String> evaluariAI = new HashMap<>();
            for(Candidat aplicare : aplicari) {
                List<CV> cvs = cvRepository.findByCandidat(aplicare);
                if(!cvs.isEmpty()) {
                    CV cvActiv = cvs.getFirst();
                    for (CV cv : cvs) if (cv.isActiv()) cvActiv = cv;
                    if(cvActiv.isActiv()) {
                        String result = agentAngajatorService.proceseaza(ocrService.extractText(cvActiv.getId()), anunt);

                        evaluariAI.put(
                                aplicare.getEmail(),
                                result
                        );
                    }
                }
            }
            model.addAttribute("id", id);
            model.addAttribute("aplicari", aplicari);
            model.addAttribute("evaluariAI", evaluariAI);
            return "angajator/aplicari";
        }
        return "redirect:/angajator/{id}";
    }
    }