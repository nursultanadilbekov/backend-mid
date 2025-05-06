package com.example.midterm_project.repositories;

import com.example.midterm_project.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by email
    Optional<User> findByEmail(String email);

    // Check if the email is already taken
    boolean existsByEmail(String email);

    // Find user by email verification token (used for 2FA)
    Optional<User> findByTwoFactorSecret(String token);

    // Find user by the verification token (optional based on your naming convention)
    Optional<User> findByVerificationToken(String token);
}
