package com.project.demo.repository;

import com.project.demo.model.Angajator;
import com.project.demo.model.Anunt;
import com.project.demo.model.Companie;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface AnuntRepository extends CrudRepository<Anunt, Integer> {
    List<Anunt>findByAngajator(Angajator angajator);
}