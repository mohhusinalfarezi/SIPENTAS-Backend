package com.sipentas.Api.repository;

import com.sipentas.Api.entity.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, Integer> {

    // Cek apakah API Key ada di database
    boolean existsByApiKey(String apiKey);

    // Cari API Key
    Optional<ApiKey> findByApiKey(String apiKey);
}