package com.project.demo.model;

import jakarta.persistence.*;
import org.springframework.lang.Nullable;


@Entity
@Table(name = "admins")
public class Admin extends User {

    public Admin() {
        this.setRol("ADMIN"); // setat automat la creare
    }

}

