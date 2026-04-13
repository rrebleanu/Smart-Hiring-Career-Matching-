package com.project.demo.repository;

import com.project.demo.model.Companie;
import org.springframework.data.repository.CrudRepository;

public interface CompanieRepository extends CrudRepository<Companie, Integer> {
}