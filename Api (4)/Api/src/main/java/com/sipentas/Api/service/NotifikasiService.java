package com.sipentas.Api.service;

import com.sipentas.Api.entity.Notifikasi;
import com.sipentas.Api.repository.NotifikasiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotifikasiService {

    @Autowired
    private NotifikasiRepository notifikasiRepository;

    public void buatNotifikasi(String nim, Integer keluhanId, String pesan) {
        Notifikasi notifikasi = new Notifikasi(nim, keluhanId, pesan);
        notifikasiRepository.save(notifikasi);
    }

    public List<Notifikasi> getNotifikasiByNim(String nim) {
        return notifikasiRepository.findByNimOrderByCreatedAtDesc(nim);
    }

    public List<Notifikasi> getNotifikasiBelumDibaca(String nim) {
        return notifikasiRepository.findByNimAndDibacaOrderByCreatedAtDesc(nim, false);
    }

    public String tandaiDibaca(Integer id) {
        Notifikasi notifikasi = notifikasiRepository.findById(id).orElse(null);
        if (notifikasi == null) {
            return "Notifikasi tidak ditemukan";
        }
        notifikasi.setDibaca(true);
        notifikasiRepository.save(notifikasi);
        return "Notifikasi ditandai sudah dibaca";
    }
}