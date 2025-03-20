package com.example.midterm_project.repositories;

import com.example.midterm_project.entities.Repairer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RepairerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RepairerRepository repairerRepository;

    @Test
    public void whenFindById_thenReturnRepairer() {
        Repairer repairer = new Repairer();
        repairer.setName("John Doe");
        repairer.setAge(30);
        repairer.setExperience("5 years");
        entityManager.persist(repairer);
        entityManager.flush();

        Optional<Repairer> found = repairerRepository.findById(repairer.getId());

        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getName()).isEqualTo(repairer.getName());
        assertThat(found.get().getAge()).isEqualTo(repairer.getAge());
        assertThat(found.get().getExperience()).isEqualTo(repairer.getExperience());
    }

    @Test
    public void whenFindAll_thenReturnAllRepairers() {
        Repairer repairer1 = new Repairer();
        repairer1.setName("John Doe");
        repairer1.setAge(30);
        repairer1.setExperience("5 years");
        entityManager.persist(repairer1);

        Repairer repairer2 = new Repairer();
        repairer2.setName("Jane Smith");
        repairer2.setAge(25);
        repairer2.setExperience("3 years");
        entityManager.persist(repairer2);

        entityManager.flush();

        List<Repairer> repairers = repairerRepository.findAll();

        assertThat(repairers).hasSize(2);
        assertThat(repairers).extracting(Repairer::getName).containsExactlyInAnyOrder("John Doe", "Jane Smith");
    }

    @Test
    public void whenSaveRepairer_thenRepairerIsSaved() {
        Repairer repairer = new Repairer();
        repairer.setName("John Doe");
        repairer.setAge(30);
        repairer.setExperience("5 years");

        Repairer savedRepairer = repairerRepository.save(repairer);

        assertThat(savedRepairer).isNotNull();
        assertThat(savedRepairer.getId()).isNotNull();
        assertThat(savedRepairer.getName()).isEqualTo("John Doe");
        assertThat(savedRepairer.getAge()).isEqualTo(30);
        assertThat(savedRepairer.getExperience()).isEqualTo("5 years");
    }

    @Test
    public void whenDeleteRepairer_thenRepairerIsRemoved() {
        Repairer repairer = new Repairer();
        repairer.setName("John Doe");
        repairer.setAge(30);
        repairer.setExperience("5 years");
        entityManager.persist(repairer);
        entityManager.flush();

        repairerRepository.delete(repairer);
        Optional<Repairer> found = repairerRepository.findById(repairer.getId());

        assertThat(found.isPresent()).isFalse();
    }
}