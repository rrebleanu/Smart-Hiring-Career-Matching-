package com.project.demo.repository;

import com.project.demo.model.CV;
import com.project.demo.model.Candidat;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CVRepository extends CrudRepository<CV, Integer> {
    List<CV> findByCandidat(Candidat candidat);
}