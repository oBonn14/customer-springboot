package com.customer.api.repository;

import com.customer.api.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        // Menyimpan customer awal untuk pengujian
        Customer customer = new Customer(null, "John Doe", 30, 1);
        customerRepository.save(customer);
    }

    @Test
    void testFindById() {
        Optional<Customer> foundCustomer = customerRepository.findById(1L);
        assertTrue(foundCustomer.isPresent(), "Customer should be found");
        assertEquals("udin", foundCustomer.get().getNameCustomer(), "Customer name should match");
    }

    @Test
    @Rollback(value = false) // Ubah ke true jika ingin database bersih setelah tes
    void testSaveCustomer() {
        Customer customer = new Customer(null, "Jane Doe", 25, 0);
        Customer savedCustomer = customerRepository.save(customer);

        assertNotNull(savedCustomer.getIdCustomer(), "Saved customer ID should not be null");
        assertEquals("Jane Doe", savedCustomer.getNameCustomer(), "Customer name should match");
    }

    @Test
    void testDeleteCustomer() {
        Customer customer = new Customer(null, "Alice", 28, 1);
        Customer savedCustomer = customerRepository.save(customer);

        customerRepository.deleteById(savedCustomer.getIdCustomer());

        Optional<Customer> deletedCustomer = customerRepository.findById(savedCustomer.getIdCustomer());
        assertFalse(deletedCustomer.isPresent(), "Deleted customer should not be found");
    }

    @Test
    void testUpdateCustomer() {
        Customer customer = new Customer(null, "Bob", 22, 1);
        Customer savedCustomer = customerRepository.save(customer);

        savedCustomer.setNameCustomer("Robert");
        customerRepository.save(savedCustomer);

        Optional<Customer> updatedCustomer = customerRepository.findById(savedCustomer.getIdCustomer());
        assertTrue(updatedCustomer.isPresent(), "Updated customer should be found");
        assertEquals("Robert", updatedCustomer.get().getNameCustomer(), "Updated name should match");
    }
}
