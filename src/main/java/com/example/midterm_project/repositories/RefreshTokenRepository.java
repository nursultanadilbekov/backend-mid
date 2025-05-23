package com.example.midterm_project.repositories;

import com.example.midterm_project.entities.RefreshToken;
import com.example.midterm_project.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
}
