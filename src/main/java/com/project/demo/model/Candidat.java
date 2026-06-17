package com.project.demo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "candidati")
public class Candidat extends User {

    // Constructor
    public Candidat() {
        this.setRol("CANDIDAT"); // setat automat la creare
    }

    // RELAȚIA NOUĂ ADAUGATĂ AICI

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
    private List<CV> cvs;

    // Getter pentru lista de CV-uri
    public List<CV> getCvs() {
        return cvs;
    }

    // Setter pentru lista de CV-uri
    public void setCvs(List<CV> cvs) {
        this.cvs = cvs;
    }


    // (Opțional) Dacă vrei pe viitor să dekomentezi și aplicările, o poți face aici:
    // @OneToMany(mappedBy = "candidat", cascade = CascadeType.ALL)
    // private List<Aplicare> aplicari;
    //
    // public List<Aplicare> getAplicari() {
    //     return aplicari;
    // }
    //
    // public void setAplicari(List<Aplicare> aplicari) {
    //     this.aplicari = aplicari;
    // }
}