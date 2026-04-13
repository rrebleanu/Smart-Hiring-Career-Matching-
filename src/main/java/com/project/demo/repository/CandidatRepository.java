package com.project.demo.repository;

import com.project.demo.model.Candidat;
import org.springframework.data.repository.CrudRepository;

public interface CandidatRepository extends CrudRepository<Candidat, Integer> {
}