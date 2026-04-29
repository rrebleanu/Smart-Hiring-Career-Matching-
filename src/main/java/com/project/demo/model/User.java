package com.project.demo.model;

import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;
import java.time.LocalDate;

@Entity // This tells Hibernate to make a table
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;

    private String numeUser;

    private LocalDate dataInfiintare;

    private String rol;

    private String parola;

    private String email;

    public @Nullable Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(@Nullable Integer idUser) {
        this.idUser = idUser;
    }

    public String getNumeUser() {
        return numeUser;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setNumeUser(String numeUser) {
        this.numeUser = numeUser;
    }

    public LocalDate getDataInfiintare() {
        return dataInfiintare;
    }

    public void setDataInfiintare(LocalDate dataInfiintare) {
        this.dataInfiintare = dataInfiintare;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}