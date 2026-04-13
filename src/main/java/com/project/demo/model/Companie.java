package com.project.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "companies")
public class Companie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCompanie; // corectat

    private String numeCompanie; // corectat

    private Double ratingCompanie; // corectat

    private Integer numarAngajati;

    public Integer getIdCompanie() {
        return idCompanie;
    }

    public void setIdCompanie(Integer idCompanie) {
        this.idCompanie = idCompanie;
    }

    public String getNumeCompanie() {
        return numeCompanie;
    }

    public void setNumeCompanie(String numeCompanie) {
        this.numeCompanie = numeCompanie;
    }

    public Double getRatingCompanie() {
        return ratingCompanie;
    }

    public void setRatingCompanie(Double ratingCompanie) {
        this.ratingCompanie = ratingCompanie;
    }

    public Integer getNumarAngajati() {
        return numarAngajati;
    }

    public void setNumarAngajati(Integer numarAngajati) {
        this.numarAngajati = numarAngajati;
    }
}