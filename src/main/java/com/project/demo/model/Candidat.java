package com.project.demo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "candidati")
public class Candidat extends User {

    public Candidat() {
        this.setRol("CANDIDAT");
    }
}