package com.project.demo.repository;

import com.project.demo.model.Anunt;
import com.project.demo.model.Companie;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface AnuntRepository extends CrudRepository<Anunt, Integer> {
    // Metodă pentru a afisa toate anunțurile unei anumite companii
//    List<Anunt> findByCompanie(Companie companie);

    // Metodă corectată pentru a afisa toate anunțurile unei anumite companii
    // In Spring o sa caut: Anunt -> Angajator -> Companie
    List<Anunt> findByAngajatorCompanie(Companie companie);

    // Metodă pentru filtrare joburi
    List<Anunt> findByDomeniuJob(String domeniuJob);
}