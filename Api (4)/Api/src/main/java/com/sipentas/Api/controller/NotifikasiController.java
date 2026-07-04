package com.sipentas.Api.controller;

import com.sipentas.Api.entity.Notifikasi;
import com.sipentas.Api.service.NotifikasiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notifikasi")
public class NotifikasiController {

    @Autowired
    private NotifikasiService notifikasiService;

    private final String VALID_API_KEY = "SIPENTAS-APP-FIXED-KEY";

    private boolean validateApiKey(String apiKey) {
        return apiKey != null && apiKey.equals(VALID_API_KEY);
    }

    private boolean validateToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return false;
        }
        String token = authHeader.substring(7);
        return token.startsWith("SIPENTAS-");
    }

    // ========== GET NOTIFIKASI BY NIM ==========
    @GetMapping
    public Object getNotifikasi(
            @RequestParam String nim,
            @RequestHeader(value = "X-API-Key", required = false) String apiKey,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        if (!validateApiKey(apiKey)) {
            return "{\"success\":false,\"message\":\"API Key tidak valid\"}";
        }
        if (!validateToken(authHeader)) {
            return "{\"success\":false,\"message\":\"Token tidak valid\"}";
        }

        return notifikasiService.getNotifikasiByNim(nim);
    }

    // ========== GET NOTIFIKASI BELUM DIBACA ==========
    @GetMapping("/unread")
    public Object getNotifikasiBelumDibaca(
            @RequestParam String nim,
            @RequestHeader(value = "X-API-Key", required = false) String apiKey,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        if (!validateApiKey(apiKey)) {
            return "{\"success\":false,\"message\":\"API Key tidak valid\"}";
        }
        if (!validateToken(authHeader)) {
            return "{\"success\":false,\"message\":\"Token tidak valid\"}";
        }

        return notifikasiService.getNotifikasiBelumDibaca(nim);
    }

    // ========== TANDAI NOTIFIKASI SUDAH DIBACA ==========
    @PutMapping("/{id}/read")
    public String tandaiDibaca(
            @PathVariable Integer id,
            @RequestHeader(value = "X-API-Key", required = false) String apiKey,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        if (!validateApiKey(apiKey)) {
            return "Gagal: API Key tidak valid";
        }
        if (!validateToken(authHeader)) {
            return "Gagal: Token tidak valid";
        }

        return notifikasiService.tandaiDibaca(id);
    }
}