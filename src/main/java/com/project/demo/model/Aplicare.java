package com.project.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "aplicari")
public class Aplicare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAplicare;

    private Double probabilitateCompatibilitate;

    @ManyToOne
    @JoinColumn(name = "id_candidat")
    private Candidat candidat;

    @ManyToOne
    @JoinColumn(name = "id_anunt")
    private Anunt anunt;


    public Integer getIdAplicare() {
        return idAplicare;
    }

    public void setIdAplicare(Integer idAplicare) {
        this.idAplicare = idAplicare;
    }

    public Double getProbabilitateCompatibilitate() {
        return probabilitateCompatibilitate;
    }

    public void setProbabilitateCompatibilitate(Double probabilitateCompatibilitate) {
        this.probabilitateCompatibilitate = probabilitateCompatibilitate;
    }

    public Candidat getCandidat() {
        return candidat;
    }

    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
    }

    public Anunt getAnunt() {
        return anunt;
    }

    public void setAnunt(Anunt anunt) {
        this.anunt = anunt;
    }
}