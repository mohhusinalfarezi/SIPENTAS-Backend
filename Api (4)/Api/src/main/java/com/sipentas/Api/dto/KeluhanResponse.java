package com.sipentas.Api.dto;

public class KeluhanResponse {

    private Integer id;
    private String nim;
    private String isiKeluhan;
    private Boolean isAnonim;
    private String createdAt;
    private String gambar;
    private String status;
    private String statusUpdatedAt;
    private String updatedBy;
    private Integer totalDukungan;
    private String ruang;  // ← TAMBAHKAN!

    // ========== CONSTRUCTORS ==========
    public KeluhanResponse() {}

    public KeluhanResponse(Integer id, String nim, String isiKeluhan, Boolean isAnonim,
                           String createdAt, String gambar, String status,
                           String statusUpdatedAt, String updatedBy, Integer totalDukungan, String ruang) {
        this.id = id;
        this.nim = nim;
        this.isiKeluhan = isiKeluhan;
        this.isAnonim = isAnonim;
        this.createdAt = createdAt;
        this.gambar = gambar;
        this.status = status;
        this.statusUpdatedAt = statusUpdatedAt;
        this.updatedBy = updatedBy;
        this.totalDukungan = totalDukungan;
        this.ruang = ruang;
    }

    // ========== GETTER & SETTER ==========
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNim() { return nim; }
    public void setNim(String nim) { this.nim = nim; }

    public String getIsiKeluhan() { return isiKeluhan; }
    public void setIsiKeluhan(String isiKeluhan) { this.isiKeluhan = isiKeluhan; }

    public Boolean getIsAnonim() { return isAnonim; }
    public void setIsAnonim(Boolean isAnonim) { this.isAnonim = isAnonim; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getGambar() { return gambar; }
    public void setGambar(String gambar) { this.gambar = gambar; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getStatusUpdatedAt() { return statusUpdatedAt; }
    public void setStatusUpdatedAt(String statusUpdatedAt) { this.statusUpdatedAt = statusUpdatedAt; }

    public String getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(String updatedBy) { this.updatedBy = updatedBy; }

    public Integer getTotalDukungan() { return totalDukungan; }
    public void setTotalDukungan(Integer totalDukungan) { this.totalDukungan = totalDukungan; }

    public String getRuang() { return ruang; }
    public void setRuang(String ruang) { this.ruang = ruang; }
}