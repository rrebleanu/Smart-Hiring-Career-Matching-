package com.project.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "angajatori")
public class Angajator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAngajator;

    @OneToOne
    @JoinColumn(name = "id_user")
    private User user;


    @ManyToOne
    @JoinColumn(name = "id_companie")
    private Companie companie;

    public Integer getIdAngajator() {
        return idAngajator;
    }

    public void setIdAngajator(Integer idAngajator) {
        this.idAngajator = idAngajator;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Companie getCompanie() {
        return companie;
    }

    public void setCompanie(Companie companie) {
        this.companie = companie;
    }
}