package com.sipentas.Api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")  // ← DIUBAH DARI "user" MENJADI "users"
public class User {

    @Id
    private String nim;
    private String password;

    public User() {
    }

    public User(String nim, String password) {
        this.nim = nim;
        this.password = password;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}