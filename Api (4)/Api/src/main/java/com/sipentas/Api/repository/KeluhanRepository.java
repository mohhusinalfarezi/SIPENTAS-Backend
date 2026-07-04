package com.sipentas.Api.repository;

import com.sipentas.Api.entity.Keluhan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeluhanRepository extends JpaRepository<Keluhan, Integer> {
    List<Keluhan> findAllByOrderByCreatedAtDesc();
    Page<Keluhan> findAll(Pageable pageable);
    List<Keluhan> findByUser_NimOrderByCreatedAtDesc(String nim);
    List<Keluhan> findAllByStatusOrderByCreatedAtDesc(String status);
    List<Keluhan> findAllByRuangOrderByCreatedAtDesc(String ruang);
}