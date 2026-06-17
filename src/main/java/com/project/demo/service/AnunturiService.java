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
    public Anunt getById(Integer id){
        return anuntRepository.findById(id).orElseThrow();
    }

    public Iterable<Anunt> getAll() {
        return anuntRepository.findAll();
    }
    public List<Anunt>AngajatorAnunturi(Angajator angajator){return anuntRepository.findByAngajator(angajator);}

    //     Metodă care extrage un anunț specific după ID-ul său
    public Anunt getAnuntById(Integer id) {
        return anuntRepository.findById(id).orElse(null);
    }

    // Metodă care șterge definitiv un anunț din baza de date
    public void deleteAnuntById(Integer id) {
        anuntRepository.deleteById(id);
    }
}
