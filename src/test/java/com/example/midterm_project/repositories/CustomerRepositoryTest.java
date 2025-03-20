package com.example.midterm_project.repositories;

import com.example.midterm_project.entities.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void whenFindById_thenReturnCustomer() {
        Customer customer = new Customer();
        customer.setName("John Doe");
        entityManager.persist(customer);
        entityManager.flush();

        Optional<Customer> found = customerRepository.findById(customer.getId());

        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getName()).isEqualTo(customer.getName());
    }

    @Test
    public void whenFindAll_thenReturnAllCustomers() {
        Customer customer1 = new Customer();
        customer1.setName("John Doe");
        entityManager.persist(customer1);

        Customer customer2 = new Customer();
        customer2.setName("Jane Smith");
        entityManager.persist(customer2);

        entityManager.flush();

        List<Customer> customers = customerRepository.findAll();

        assertThat(customers).hasSize(2);
        assertThat(customers).extracting(Customer::getName).containsExactlyInAnyOrder("John Doe", "Jane Smith");
    }

    @Test
    public void whenSaveCustomer_thenCustomerIsSaved() {
        Customer customer = new Customer();
        customer.setName("John Doe");

        Customer savedCustomer = customerRepository.save(customer);

        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getId()).isNotNull();
        assertThat(savedCustomer.getName()).isEqualTo("John Doe");
    }

    @Test
    public void whenDeleteCustomer_thenCustomerIsRemoved() {
        Customer customer = new Customer();
        customer.setName("John Doe");
        entityManager.persist(customer);
        entityManager.flush();

        customerRepository.delete(customer);
        Optional<Customer> found = customerRepository.findById(customer.getId());

        assertThat(found.isPresent()).isFalse();
    }
}