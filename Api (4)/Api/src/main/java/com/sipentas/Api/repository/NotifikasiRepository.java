package com.sipentas.Api.repository;

import com.sipentas.Api.entity.Notifikasi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotifikasiRepository extends JpaRepository<Notifikasi, Integer> {

    List<Notifikasi> findByNimOrderByCreatedAtDesc(String nim);

    List<Notifikasi> findByNimAndDibacaOrderByCreatedAtDesc(String nim, Boolean dibaca);
}