package com.sipentas.Api.controller;

import com.sipentas.Api.entity.User;
import com.sipentas.Api.repository.UserRepository;
import com.sipentas.Api.service.DukunganService;
import com.sipentas.Api.service.KeluhanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/keluhan")
public class KeluhanController {

    @Autowired
    private KeluhanService keluhanService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DukunganService dukunganService;

    private final String VALID_API_KEY = "SIPENTAS-APP-FIXED-KEY";
    private final String UPLOAD_DIR = "uploads/";

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

    // ========== 1. GET SEMUA KELUHAN + FILTER ==========
    @GetMapping
    public Object getSemuaKeluhan(
            @RequestParam(required = false) String nim,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String ruang,
            @RequestHeader(value = "X-API-Key", required = false) String apiKey,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        if (!validateApiKey(apiKey)) {
            return "{\"success\":false,\"message\":\"API Key tidak valid\"}";
        }
        if (!validateToken(authHeader)) {
            return "{\"success\":false,\"message\":\"Token tidak valid\"}";
        }

        if (nim != null && !nim.isEmpty()) {
            return keluhanService.ambilKeluhanByNim(nim);
        }

        if (status != null && !status.isEmpty()) {
            return keluhanService.ambilKeluhanByStatus(status);
        }

        if (ruang != null && !ruang.isEmpty()) {
            return keluhanService.ambilKeluhanByRuang(ruang);
        }

        return keluhanService.ambilSemuaKeluhan();
    }

    // ========== 2. TAMBAH KELUHAN (TANPA GAMBAR) ==========
    @PostMapping
    public String tambahKeluhan(
            @RequestParam String nim,
            @RequestParam String isiKeluhan,
            @RequestParam Boolean isAnonim,
            @RequestParam(required = false) String ruang,
            @RequestHeader(value = "X-API-Key", required = false) String apiKey,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        if (!validateApiKey(apiKey)) return "Gagal: API Key tidak valid";
        if (!validateToken(authHeader)) return "Gagal: Token tidak valid atau tidak ada";

        User user = userRepository.findById(nim).orElse(null);
        if (user == null) {
            return "Gagal: User dengan NIM tersebut tidak ditemukan di sistem!";
        }

        keluhanService.buatKeluhan(user, isiKeluhan, isAnonim, null, ruang);
        return "Berhasil: Keluhan kamu sudah tersimpan!";
    }

    // ========== 3. TAMBAH KELUHAN + UPLOAD GAMBAR ==========
    @PostMapping("/upload")
    public String tambahKeluhanDenganGambar(
            @RequestParam String nim,
            @RequestParam String isiKeluhan,
            @RequestParam Boolean isAnonim,
            @RequestParam(required = false) String ruang,
            @RequestParam("file") MultipartFile file,
            @RequestHeader(value = "X-API-Key", required = false) String apiKey,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        if (!validateApiKey(apiKey)) return "Gagal: API Key tidak valid";
        if (!validateToken(authHeader)) return "Gagal: Token tidak valid atau tidak ada";

        User user = userRepository.findById(nim).orElse(null);
        if (user == null) {
            return "Gagal: User dengan NIM tersebut tidak ditemukan di sistem!";
        }

        String fileName = null;
        try {
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            fileName = UUID.randomUUID().toString() + extension;

            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            Files.write(filePath, file.getBytes());

        } catch (IOException e) {
            return "Gagal: Gagal upload gambar - " + e.getMessage();
        }

        keluhanService.buatKeluhan(user, isiKeluhan, isAnonim, fileName, ruang);
        return "Berhasil: Keluhan + gambar sudah tersimpan!";
    }

    // ========== 4. UPDATE STATUS LAPORAN ==========
    @PutMapping("/{id}/status")
    public String updateStatusKeluhan(
            @PathVariable Integer id,
            @RequestParam String statusBaru,
            @RequestParam String level,
            @RequestParam String updatedBy,
            @RequestHeader(value = "X-API-Key", required = false) String apiKey,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        if (!validateApiKey(apiKey)) return "Gagal: API Key tidak valid";
        if (!validateToken(authHeader)) return "Gagal: Token tidak valid";

        return keluhanService.updateStatus(id, statusBaru, level, updatedBy);
    }

    // ========== 5. TAMBAH DUKUNGAN (POLLING) ==========
    @PostMapping("/{id}/dukung")
    public String tambahDukungan(
            @PathVariable Integer id,
            @RequestParam String nim,
            @RequestParam(required = false, defaultValue = "false") Boolean isAnonim,
            @RequestHeader(value = "X-API-Key", required = false) String apiKey,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        if (!validateApiKey(apiKey)) return "Gagal: API Key tidak valid";
        if (!validateToken(authHeader)) return "Gagal: Token tidak valid";

        return dukunganService.tambahDukungan(id, nim, isAnonim);
    }

    // ========== 6. BATAL DUKUNGAN ==========
    @DeleteMapping("/{id}/batal-dukung")
    public String hapusDukungan(
            @PathVariable Integer id,
            @RequestParam String nim,
            @RequestHeader(value = "X-API-Key", required = false) String apiKey,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        if (!validateApiKey(apiKey)) return "Gagal: API Key tidak valid";
        if (!validateToken(authHeader)) return "Gagal: Token tidak valid";

        return dukunganService.hapusDukungan(id, nim);
    }

    // ========== 7. CEK SUDAH DUKUNG ==========
    @GetMapping("/{id}/cek-dukungan")
    public Object cekDukungan(
            @PathVariable Integer id,
            @RequestParam String nim,
            @RequestHeader(value = "X-API-Key", required = false) String apiKey,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        if (!validateApiKey(apiKey)) {
            return "{\"success\":false,\"message\":\"API Key tidak valid\"}";
        }
        if (!validateToken(authHeader)) {
            return "{\"success\":false,\"message\":\"Token tidak valid\"}";
        }

        boolean sudah = dukunganService.sudahMendukung(id, nim);
        return "{\"sudahMendukung\": " + sudah + "}";
    }
}