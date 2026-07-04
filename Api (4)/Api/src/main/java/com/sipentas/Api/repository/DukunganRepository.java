package com.sipentas.Api.repository;

import com.sipentas.Api.entity.Dukungan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DukunganRepository extends JpaRepository<Dukungan, Integer> {

    int countByKeluhanId(Integer keluhanId);

    boolean existsByKeluhanIdAndNim(Integer keluhanId, String nim);

    void deleteByKeluhanIdAndNim(Integer keluhanId, String nim);
}