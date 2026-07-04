package com.sipentas.Api.service;

import com.sipentas.Api.entity.Dukungan;
import com.sipentas.Api.entity.Keluhan;
import com.sipentas.Api.repository.DukunganRepository;
import com.sipentas.Api.repository.KeluhanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DukunganService {

    @Autowired
    private DukunganRepository dukunganRepository;

    @Autowired
    private KeluhanRepository keluhanRepository;

    @Autowired
    private NotifikasiService notifikasiService;

    // ========== TAMBAH DUKUNGAN ==========
    @Transactional
    public String tambahDukungan(Integer keluhanId, String nim, Boolean isAnonim) {
        if (dukunganRepository.existsByKeluhanIdAndNim(keluhanId, nim)) {
            return "Anda sudah mendukung laporan ini";
        }
        Dukungan dukungan = new Dukungan(keluhanId, nim, isAnonim);
        dukunganRepository.save(dukungan);

        // 🔔 KIRIM NOTIFIKASI
        Keluhan keluhan = keluhanRepository.findById(keluhanId).orElse(null);
        if (keluhan != null) {
            String namaPendukung = (isAnonim != null && isAnonim) ? "Anonim" : "NIM: " + nim;
            String pesan = "Laporan #" + keluhanId + " mendapat dukungan dari " + namaPendukung;
            notifikasiService.buatNotifikasi(keluhan.getUser().getNim(), keluhanId, pesan);
        }

        return "Berhasil mendukung laporan";
    }

    // ========== BATAL DUKUNGAN ==========
    @Transactional
    public String hapusDukungan(Integer keluhanId, String nim) {
        if (!dukunganRepository.existsByKeluhanIdAndNim(keluhanId, nim)) {
            return "Anda belum mendukung laporan ini";
        }
        dukunganRepository.deleteByKeluhanIdAndNim(keluhanId, nim);
        return "Berhasil membatalkan dukungan";
    }

    // ========== TOTAL DUKUNGAN ==========
    public int getTotalDukungan(Integer keluhanId) {
        return dukunganRepository.countByKeluhanId(keluhanId);
    }

    // ========== CEK SUDAH DUKUNG ==========
    public boolean sudahMendukung(Integer keluhanId, String nim) {
        return dukunganRepository.existsByKeluhanIdAndNim(keluhanId, nim);
    }
}