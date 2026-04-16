package com.project.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cvs")
public class CV {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCv;

    private String descriereCandidat;
    private String telefon;
    private String experienta;
    private String domeniu;

    @ManyToOne
    @JoinColumn(name = "id_candidat")
    private Candidat candidate;

//    @OneToOne(mappedBy = "cv")
//    private Candidat candidate;

    public Integer getIdCv() {
        return idCv;
    }

    public void setIdCv(Integer idCv) {
        this.idCv = idCv;
    }

    public String getDescriereCandidat() {
        return descriereCandidat;
    }

    public void setDescriereCandidat(String descriereCandidat) {
        this.descriereCandidat = descriereCandidat;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getExperienta() {
        return experienta;
    }

    public void setExperienta(String experienta) {
        this.experienta = experienta;
    }

    public String getDomeniu() {
        return domeniu;
    }

    public void setDomeniu(String domeniu) {
        this.domeniu = domeniu;
    }

    public Candidat getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidat candidate) {
        this.candidate = candidate;
    }

}
