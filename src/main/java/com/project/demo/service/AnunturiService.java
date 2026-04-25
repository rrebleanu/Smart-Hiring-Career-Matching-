package com.project.demo.service;

import com.project.demo.model.Anunt;
import com.project.demo.repository.AnuntRepository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class AnunturiService {

    private final AnuntRepository anuntRepository;
    public AnunturiService(AnuntRepository anuntRepository){
        this.anuntRepository = anuntRepository;
    }

    public String addNewAnunt (Anunt newAnunt) {
        anuntRepository.save(newAnunt);
        return "Job Advertisement Saved Successfully";
    }

    public Iterable<Anunt> getAnunturi() {
        return anuntRepository.findAll();
    }
}
