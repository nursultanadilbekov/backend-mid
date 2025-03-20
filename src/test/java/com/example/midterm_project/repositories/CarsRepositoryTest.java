package com.example.midterm_project.repositories;

import com.example.midterm_project.entities.Car;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CarsRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarsRepository carsRepository;

    @Test
    public void whenFindByNumber_thenReturnCar() {
        Car car = new Car();
        car.setNumber("ABC123");
        entityManager.persist(car);
        entityManager.flush();

        Optional<Car> found = carsRepository.findByNumber(car.getNumber());

        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getNumber()).isEqualTo(car.getNumber());
    }

    @Test
    public void whenFindByNumber_thenReturnEmpty() {
        String nonExistentNumber = "XYZ789";

        Optional<Car> found = carsRepository.findByNumber(nonExistentNumber);

        assertThat(found.isPresent()).isFalse();
    }
}