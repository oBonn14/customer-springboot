package com.customer.api.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerTest {

    @Test
    void testCustomerGettersAndSetters() {
        Customer customer = new Customer();
        customer.setIdCustomer(1L);
        customer.setNameCustomer("John Doe");
        customer.setAge(30);
        customer.setGender(1); // Assuming 1 is for male

        assertEquals(1L, customer.getIdCustomer());
        assertEquals("John Doe", customer.getNameCustomer());
        assertEquals(30, customer.getAge());
        assertEquals(1, customer.getGender());
    }

    @Test
    void testCustomerBuilder() {
        Customer customer = Customer.builder()
                .idCustomer(2L)
                .nameCustomer("Jane Doe")
                .age(25)
                .gender(0) // Assuming 0 is for female
                .build();

        assertEquals(2L, customer.getIdCustomer());
        assertEquals("Jane Doe", customer.getNameCustomer());
        assertEquals(25, customer.getAge());
        assertEquals(0, customer.getGender());
    }

    @Test
    void testCustomerNoArgsConstructor() {
        Customer customer = new Customer();
        assertEquals(null, customer.getIdCustomer());
        assertEquals(null, customer.getNameCustomer());
        assertEquals(0, customer.getAge());
        assertEquals(0, customer.getGender());
    }

    @Test
    void testCustomerAllArgsConstructor() {
        Customer customer = new Customer(3L, "Alice", 28, 1);

        assertEquals(3L, customer.getIdCustomer());
        assertEquals("Alice", customer.getNameCustomer());
        assertEquals(28, customer.getAge());
        assertEquals(1, customer.getGender());
    }
}
