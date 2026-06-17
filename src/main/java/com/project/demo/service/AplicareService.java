package com.project.demo.service;

import com.project.demo.model.Anunt;
import com.project.demo.model.Aplicare;
import com.project.demo.model.Candidat;
import com.project.demo.model.User;
import com.project.demo.repository.AplicareRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AplicareService {
    private final AplicareRepository aplicareRepository;

    public AplicareService(AplicareRepository aplicareRepository)
    {
        this.aplicareRepository = aplicareRepository;
    }
    public void aplica(Candidat user, Anunt anunt)
    {
        Aplicare aplicare = new Aplicare();
        aplicare.setCandidat(user);
        aplicare.setAnunt(anunt);
        aplicareRepository.save(aplicare);
    }
    public Set<Integer> anunturiAplicate(Candidat candidat){
        return aplicareRepository.findByCandidat(candidat)
                .stream()
                .map(a -> a.getAnunt().getId())
                .collect(Collectors.toSet());
    }
    public List<Candidat> candidati(Anunt anunt){

        return aplicareRepository.findByAnunt(anunt)
                .stream()
                .map(Aplicare::getCandidat)
                .collect(Collectors.toList());
    }
}
