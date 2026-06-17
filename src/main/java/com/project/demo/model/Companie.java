package com.project.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "companii")
public class Companie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // corectat

    private String numeCompanie; // corectat

    private String locatie;

    private String domeniu;

    public String getDomeniu() {
        return domeniu;
    }

    public void setDomeniu(String domeniu) {
        this.domeniu = domeniu;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer idCompanie) {
        this.id = idCompanie;
    }

    public String getNumeCompanie() {
        return numeCompanie;
    }

    public void setNumeCompanie(String numeCompanie) {
        this.numeCompanie = numeCompanie;
    }


}