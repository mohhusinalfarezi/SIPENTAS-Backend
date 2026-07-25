package com.sipentas.Api.repository;

import com.sipentas.Api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // Cari user berdasarkan NIM dan Password (untuk login)
    Optional<User> findByNimAndPassword(String nim, String password);
    Optional<User> findByNim(String nim);
    boolean existsByNim(String nim);
    void deleteByNim(String nim);
}