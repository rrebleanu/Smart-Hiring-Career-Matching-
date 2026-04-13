package com.project.demo.repository;

import com.project.demo.model.Anunt;
import com.project.demo.model.Companie;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface AnuntRepository extends CrudRepository<Anunt, Integer> {
    // Metodă pentru a afisa toate anunțurile unei anumite companii
    List<Anunt> findByCompanie(Companie companie);

    // Metodă pentru filtrare joburi
    List<Anunt> findByDomeniuJob(String domeniuJob);
}