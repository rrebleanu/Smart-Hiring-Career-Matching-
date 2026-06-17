package com.project.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "angajatori")
public class Angajator extends User{

    public Angajator() {
        this.setRol("ANGAJATOR"); // setat automat la creare
    }


    @ManyToOne
    @JoinColumn(name = "id_companie")
    private Companie companie;



    public Companie getCompanie() {
        return companie;
    }

    public void setCompanie(Companie companie) {
        this.companie = companie;
    }

}