package com.project.demo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "candidates")
public class Candidat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCandidat;

    @OneToOne
    @JoinColumn(name = "id_user")
    private User user;

    @OneToOne
    @JoinColumn(name = "id_cv")
    private CV cv;

    @OneToMany(mappedBy = "candidat")
    private List<Aplicare> aplicari;


    public Integer getIdCandidat() {
        return idCandidat;
    }

    public void setIdCandidat(Integer idCandidat) {
        this.idCandidat = idCandidat;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CV getCv() {
        return cv;
    }

    public void setCv(CV cv) {
        this.cv = cv;
    }

    // Getteri și setteri pentru noua listă
    public List<Aplicare> getAplicari() {
        return aplicari;
    }

    public void setAplicari(List<Aplicare> aplicari) {
        this.aplicari = aplicari;
    }
}