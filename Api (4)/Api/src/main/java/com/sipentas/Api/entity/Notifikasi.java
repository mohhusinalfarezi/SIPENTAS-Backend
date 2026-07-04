package com.sipentas.Api.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifikasi")
public class Notifikasi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nim", nullable = false)
    private String nim;

    @Column(name = "keluhan_id")
    private Integer keluhanId;

    @Column(name = "pesan", nullable = false, columnDefinition = "TEXT")
    private String pesan;

    @Column(name = "dibaca")
    private Boolean dibaca = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // ========== CONSTRUCTORS ==========
    public Notifikasi() {}

    public Notifikasi(String nim, Integer keluhanId, String pesan) {
        this.nim = nim;
        this.keluhanId = keluhanId;
        this.pesan = pesan;
        this.dibaca = false;
        this.createdAt = LocalDateTime.now();
    }

    // ========== GETTER & SETTER ==========
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNim() { return nim; }
    public void setNim(String nim) { this.nim = nim; }

    public Integer getKeluhanId() { return keluhanId; }
    public void setKeluhanId(Integer keluhanId) { this.keluhanId = keluhanId; }

    public String getPesan() { return pesan; }
    public void setPesan(String pesan) { this.pesan = pesan; }

    public Boolean getDibaca() { return dibaca; }
    public void setDibaca(Boolean dibaca) { this.dibaca = dibaca; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}