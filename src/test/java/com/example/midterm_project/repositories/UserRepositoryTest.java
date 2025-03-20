package com.example.midterm_project.repositories;

import com.example.midterm_project.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindByEmail_thenReturnUser() {
        User user = new User();
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        entityManager.persist(user);
        entityManager.flush();

        Optional<User> found = userRepository.findByEmail(user.getEmail());

        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    public void whenFindByEmail_thenReturnEmpty() {
        String nonExistentEmail = "nonexistent@example.com";

        Optional<User> found = userRepository.findByEmail(nonExistentEmail);

        assertThat(found.isPresent()).isFalse();
    }

    @Test
    public void whenSaveUser_thenUserIsSaved() {
        User user = new User();
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");

        User savedUser = userRepository.save(user);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(savedUser.getPassword()).isEqualTo("password123");
    }

    @Test
    public void whenDeleteUser_thenUserIsRemoved() {
        User user = new User();
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        entityManager.persist(user);
        entityManager.flush();

        userRepository.delete(user);
        Optional<User> found = userRepository.findById(user.getId());

        assertThat(found.isPresent()).isFalse();
    }
}