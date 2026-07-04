package com.sipentas.Api.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "dukungan")
public class Dukungan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "keluhan_id", nullable = false)
    private Integer keluhanId;

    @Column(name = "nim", nullable = false)
    private String nim;

    @Column(name = "is_anonim")
    private Boolean isAnonim;  // ← TAMBAHKAN INI!

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Dukungan() {}

    public Dukungan(Integer keluhanId, String nim, Boolean isAnonim) {
        this.keluhanId = keluhanId;
        this.nim = nim;
        this.isAnonim = isAnonim != null && isAnonim;
        this.createdAt = LocalDateTime.now();
    }

    // Getter & Setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getKeluhanId() { return keluhanId; }
    public void setKeluhanId(Integer keluhanId) { this.keluhanId = keluhanId; }

    public String getNim() { return nim; }
    public void setNim(String nim) { this.nim = nim; }

    public Boolean getIsAnonim() { return isAnonim; }
    public void setIsAnonim(Boolean isAnonim) { this.isAnonim = isAnonim; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}