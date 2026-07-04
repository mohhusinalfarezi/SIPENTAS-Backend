package com.sipentas.Api.service;

import com.sipentas.Api.dto.KeluhanResponse;
import com.sipentas.Api.entity.Keluhan;
import com.sipentas.Api.entity.User;
import com.sipentas.Api.repository.KeluhanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KeluhanService {

    @Autowired
    private KeluhanRepository keluhanRepository;

    @Autowired
    private NotifikasiService notifikasiService;

    @Autowired
    private DukunganService dukunganService;

    // ========== BUAT KELUHAN ==========
    public void buatKeluhan(User user, String isiKeluhan, Boolean isAnonim, String gambar, String ruang) {
        Keluhan keluhan = new Keluhan(isiKeluhan, isAnonim, user);
        keluhan.setGambar(gambar);
        keluhan.setStatus("DIAJUKAN");
        keluhan.setStatusUpdatedAt(LocalDateTime.now());
        keluhan.setUpdatedBy("Sistem");
        keluhan.setRuang(ruang);  // ← TAMBAHKAN!
        keluhanRepository.save(keluhan);
    }

    // ========== AMBIL SEMUA KELUHAN ==========
    public List<KeluhanResponse> ambilSemuaKeluhan() {
        List<Keluhan> keluhanList = keluhanRepository.findAllByOrderByCreatedAtDesc();

        return keluhanList.stream().map(keluhan -> {
            String nama = keluhan.getIsAnonim() ? "Anonim" : keluhan.getUser().getNim();
            int totalDukungan = dukunganService.getTotalDukungan(keluhan.getId());

            return new KeluhanResponse(
                    keluhan.getId(),
                    nama,
                    keluhan.getIsiKeluhan(),
                    keluhan.getIsAnonim(),
                    keluhan.getCreatedAt().toString(),
                    keluhan.getGambar(),
                    keluhan.getStatus(),
                    keluhan.getStatusUpdatedAt() != null ?
                            keluhan.getStatusUpdatedAt().toString() : null,
                    keluhan.getUpdatedBy(),
                    totalDukungan,
                    keluhan.getRuang()  // ← TAMBAHKAN!
            );
        }).collect(Collectors.toList());
    }

    // ========== AMBIL KELUHAN BERDASARKAN NIM ==========
    public List<KeluhanResponse> ambilKeluhanByNim(String nim) {
        List<Keluhan> keluhanList = keluhanRepository.findByUser_NimOrderByCreatedAtDesc(nim);

        return keluhanList.stream().map(keluhan -> {
            String nama = keluhan.getIsAnonim() ? "Anonim" : keluhan.getUser().getNim();
            int totalDukungan = dukunganService.getTotalDukungan(keluhan.getId());

            return new KeluhanResponse(
                    keluhan.getId(),
                    nama,
                    keluhan.getIsiKeluhan(),
                    keluhan.getIsAnonim(),
                    keluhan.getCreatedAt().toString(),
                    keluhan.getGambar(),
                    keluhan.getStatus(),
                    keluhan.getStatusUpdatedAt() != null ?
                            keluhan.getStatusUpdatedAt().toString() : null,
                    keluhan.getUpdatedBy(),
                    totalDukungan,
                    keluhan.getRuang()  // ← TAMBAHKAN!
            );
        }).collect(Collectors.toList());
    }

    // ========== AMBIL KELUHAN BERDASARKAN STATUS ==========
    public List<KeluhanResponse> ambilKeluhanByStatus(String status) {
        List<Keluhan> keluhanList = keluhanRepository.findAllByStatusOrderByCreatedAtDesc(status);

        return keluhanList.stream().map(keluhan -> {
            String nama = keluhan.getIsAnonim() ? "Anonim" : keluhan.getUser().getNim();
            int totalDukungan = dukunganService.getTotalDukungan(keluhan.getId());

            return new KeluhanResponse(
                    keluhan.getId(),
                    nama,
                    keluhan.getIsiKeluhan(),
                    keluhan.getIsAnonim(),
                    keluhan.getCreatedAt().toString(),
                    keluhan.getGambar(),
                    keluhan.getStatus(),
                    keluhan.getStatusUpdatedAt() != null ?
                            keluhan.getStatusUpdatedAt().toString() : null,
                    keluhan.getUpdatedBy(),
                    totalDukungan,
                    keluhan.getRuang()  // ← TAMBAHKAN!
            );
        }).collect(Collectors.toList());
    }

    // ========== ✅ AMBIL KELUHAN BERDASARKAN RUANG ==========
    public List<KeluhanResponse> ambilKeluhanByRuang(String ruang) {
        List<Keluhan> keluhanList = keluhanRepository.findAllByRuangOrderByCreatedAtDesc(ruang);

        return keluhanList.stream().map(keluhan -> {
            String nama = keluhan.getIsAnonim() ? "Anonim" : keluhan.getUser().getNim();
            int totalDukungan = dukunganService.getTotalDukungan(keluhan.getId());

            return new KeluhanResponse(
                    keluhan.getId(),
                    nama,
                    keluhan.getIsiKeluhan(),
                    keluhan.getIsAnonim(),
                    keluhan.getCreatedAt().toString(),
                    keluhan.getGambar(),
                    keluhan.getStatus(),
                    keluhan.getStatusUpdatedAt() != null ?
                            keluhan.getStatusUpdatedAt().toString() : null,
                    keluhan.getUpdatedBy(),
                    totalDukungan,
                    keluhan.getRuang()
            );
        }).collect(Collectors.toList());
    }

    // ========== UPDATE STATUS LAPORAN ==========
    public String updateStatus(Integer keluhanId, String statusBaru, String level, String updatedBy) {
        Keluhan keluhan = keluhanRepository.findById(keluhanId).orElse(null);
        if (keluhan == null) {
            return "Laporan tidak ditemukan";
        }

        if (!isValidStatusTransition(keluhan.getStatus(), statusBaru, level)) {
            return "Status tidak valid untuk level ini";
        }

        String statusLama = keluhan.getStatus();

        keluhan.setStatus(statusBaru);
        keluhan.setStatusUpdatedAt(LocalDateTime.now());
        keluhan.setUpdatedBy(updatedBy);
        keluhanRepository.save(keluhan);

        if (!statusLama.equals(statusBaru)) {
            String pesan = String.format(
                    "Laporan #%d berubah status dari '%s' menjadi '%s' oleh %s",
                    keluhanId, statusLama, statusBaru, updatedBy
            );
            notifikasiService.buatNotifikasi(keluhan.getUser().getNim(), keluhanId, pesan);
        }

        return "Status berhasil diupdate oleh " + updatedBy + " menjadi: " + statusBaru;
    }

    // ========== VALIDASI TRANSISI STATUS ==========
    private boolean isValidStatusTransition(String statusSekarang, String statusBaru, String level) {
        if ("PRODI".equals(level)) {
            return (statusSekarang.equals("DIAJUKAN") || statusSekarang.equals("DIPROSES_PRODI")) &&
                    (statusBaru.equals("DIPROSES_PRODI") ||
                            statusBaru.equals("SELESAI") ||
                            statusBaru.equals("DITERUSKAN_FAKULTAS"));
        }

        if ("FAKULTAS".equals(level)) {
            return (statusSekarang.equals("DITERUSKAN_FAKULTAS") || statusSekarang.equals("DIPROSES_FAKULTAS")) &&
                    (statusBaru.equals("DIPROSES_FAKULTAS") ||
                            statusBaru.equals("SELESAI") ||
                            statusBaru.equals("DITERUSKAN_REKTORAT"));
        }

        if ("REKTORAT".equals(level)) {
            return (statusSekarang.equals("DITERUSKAN_REKTORAT") || statusSekarang.equals("DIPROSES_REKTORAT")) &&
                    (statusBaru.equals("DIPROSES_REKTORAT") ||
                            statusBaru.equals("SELESAI") ||
                            statusBaru.equals("TIDAK_DAPAT_DITANGANI"));
        }

        return false;
    }
}