package com.project.demo.service;

import com.project.demo.model.Angajator;
import com.project.demo.model.Anunt;
import com.project.demo.repository.AnuntRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class AnunturiService {

    private final AnuntRepository anuntRepository;
    public AnunturiService(AnuntRepository anuntRepository){
        this.anuntRepository = anuntRepository;
    }

    public void save(Anunt newAnunt) {
        anuntRepository.save(newAnunt);
    }

    public Iterable<Anunt> getAll() {
        return anuntRepository.findAll();
    }
    public List<Anunt>AngajatorAnunturi(Angajator angajator){return anuntRepository.findByAngajator(angajator);}
}
