package com.sipentas.Api.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "api_keys")
public class ApiKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String apiKey;

    private String appName;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public ApiKey() {}

    public ApiKey(String apiKey, String appName) {
        this.apiKey = apiKey;
        this.appName = appName;
        this.createdAt = LocalDateTime.now();
    }

    // Getter & Setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getApiKey() { return apiKey; }
    public void setApiKey(String apiKey) { this.apiKey = apiKey; }

    public String getAppName() { return appName; }
    public void setAppName(String appName) { this.appName = appName; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}