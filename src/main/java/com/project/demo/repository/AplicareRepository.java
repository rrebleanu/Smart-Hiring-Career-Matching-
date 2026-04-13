package com.project.demo.repository;

import com.project.demo.model.Aplicare;
import com.project.demo.model.Candidat;
import com.project.demo.model.Anunt;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface AplicareRepository extends CrudRepository<Aplicare, Integer> {
    // Pentru a vedea istoricul de aplicari
    List<Aplicare> findByCandidat(Candidat candidat);
    List<Aplicare> findByAnunt(Anunt anunt);
}