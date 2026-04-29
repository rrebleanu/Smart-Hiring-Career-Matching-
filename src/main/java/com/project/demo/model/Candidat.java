package com.project.demo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "candidati")
public class Candidat extends User {

    public Candidat() {
        this.setRol("CANDIDAT"); // setat automat la creare
    }

//    public CV getCv() {
//        return cv;
//    }

//    public void setCv(CV cv) {
//        this.cv = cv;
//    }

//    // Getteri și setteri pentru noua listă
//    public List<Aplicare> getAplicari() {
//        return aplicari;
//    }
//
//    public void setAplicari(List<Aplicare> aplicari) {
//        this.aplicari = aplicari;
//    }
}