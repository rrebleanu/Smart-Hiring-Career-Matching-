package com.project.demo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "candidati")
public class Candidat extends User {

    private String cvPath; // The physical PDF path we set up earlier

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_cv") // Links to the CV table
    private CV cv;

    public Candidat() {
        this.setRol("CANDIDAT");
    }

    public String getCvPath() { return cvPath; }
    public void setCvPath(String cvPath) { this.cvPath = cvPath; }

    public CV getCv() { return cv; }
    public void setCv(CV cv) { this.cv = cv; }
}