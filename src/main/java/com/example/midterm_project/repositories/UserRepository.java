package com.example.midterm_project.repositories;

import com.example.midterm_project.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email); // Already exists

    boolean existsByEmail(String email); // New method to check if the email is already taken
}
