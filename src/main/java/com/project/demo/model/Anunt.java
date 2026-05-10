package com.project.demo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "anunturi")
public class Anunt{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titlu;

    @Column(length = 1000) // Descrierile pot fi lungi
    private String descriereJob;

    private Double salariuMin;
    private Double salariuMax;
    private String timpDeLucru;
    private String timpExperientaNecesar;
    private String domeniuJob;


    @ManyToOne
    @JoinColumn(name = "id_angajator")
    private Angajator angajator;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitlu() { return titlu; }
    public void setTitlu(String titlu) { this.titlu = titlu; }

    public String getDescriereJob() { return descriereJob; }
    public void setDescriereJob(String descriereJob) { this.descriereJob = descriereJob; }

    public Double getSalariuMin() { return salariuMin; }
    public void setSalariuMin(Double salariuMin) { this.salariuMin = salariuMin; }

    public Double getSalariuMax() { return salariuMax; }
    public void setSalariuMax(Double salariuMax) { this.salariuMax = salariuMax; }

    public String getTimpDeLucru() { return timpDeLucru; }
    public void setTimpDeLucru(String timpDeLucru) { this.timpDeLucru = timpDeLucru; }

    public String getTimpExperientaNecesar() { return timpExperientaNecesar; }
    public void setTimpExperientaNecesar(String timpExperientaNecesar) { this.timpExperientaNecesar = timpExperientaNecesar; }

    public String getDomeniuJob() { return domeniuJob; }
    public void setDomeniuJob(String domeniuJob) { this.domeniuJob = domeniuJob; }
    public Angajator getAngajator() { return angajator; }
    public void setAngajator(Angajator angajator) { this.angajator = angajator; }
}