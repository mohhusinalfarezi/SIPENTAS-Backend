package com.sipentas.Api.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "keluhan")
public class Keluhan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String isiKeluhan;

    @Column(name = "is_anonim", nullable = false)
    private Boolean isAnonim;

    @Column(name = "gambar")
    private String gambar;

    @Column(name = "status")
    private String status;

    @Column(name = "status_updated_at")
    private LocalDateTime statusUpdatedAt;

    @Column(name = "updated_by")
    private String updatedBy;

    // ✅ TAMBAHKAN FIELD RUANG
    @Column(name = "ruang")
    private String ruang;

    @ManyToOne
    @JoinColumn(name = "nim", referencedColumnName = "nim", nullable = false)
    private User user;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // ========== CONSTRUCTORS ==========
    public Keluhan() {}

    public Keluhan(String isiKeluhan, Boolean isAnonim, User user) {
        this.isiKeluhan = isiKeluhan;
        this.isAnonim = isAnonim;
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.status = "DIAJUKAN";
        this.statusUpdatedAt = LocalDateTime.now();
        this.updatedBy = "Sistem";
    }

    // ========== GETTER & SETTER ==========
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getIsiKeluhan() { return isiKeluhan; }
    public void setIsiKeluhan(String isiKeluhan) { this.isiKeluhan = isiKeluhan; }

    public Boolean getIsAnonim() { return isAnonim; }
    public void setIsAnonim(Boolean isAnonim) { this.isAnonim = isAnonim; }

    public String getGambar() { return gambar; }
    public void setGambar(String gambar) { this.gambar = gambar; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getStatusUpdatedAt() { return statusUpdatedAt; }
    public void setStatusUpdatedAt(LocalDateTime statusUpdatedAt) { this.statusUpdatedAt = statusUpdatedAt; }

    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }

    // ✅ GETTER & SETTER UNTUK RUANG
    public String getRuang() { return ruang; }
    public void setRuang(String ruang) { this.ruang = ruang; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}