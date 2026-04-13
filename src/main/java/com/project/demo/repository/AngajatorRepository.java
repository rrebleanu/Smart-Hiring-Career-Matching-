package com.project.demo.repository;

import com.project.demo.model.Angajator;
import org.springframework.data.repository.CrudRepository;

public interface AngajatorRepository extends CrudRepository<Angajator, Integer> {
}