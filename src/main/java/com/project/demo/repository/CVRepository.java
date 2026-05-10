package com.project.demo.repository;

import com.project.demo.model.CV;
import com.project.demo.model.Candidat;
import org.jspecify.annotations.Nullable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CVRepository extends CrudRepository<CV, Integer> {
    List<CV> findByCandidat(Candidat candidat);
    List<CV> findByFileType(String fileType);

    Optional<CV> findByActiv(boolean b);
    Optional<CV> findByCandidatAndActiv(Candidat candidat, boolean activ);
}