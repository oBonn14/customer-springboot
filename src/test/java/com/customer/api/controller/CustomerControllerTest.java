package com.customer.api.controller;

import com.customer.api.model.Customer;
import com.customer.api.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        customer.setIdCustomer(1L);
        customer.setNameCustomer("John Doe");
        customer.setAge(20);

        customers.add(customer);


        when(customerService.getAllCustomer()).thenReturn(customers);

        List<Customer> result = customerController.getAllCustomers();

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getNameCustomer());
    }

    @Test
    void testAddCustomer() {
        Customer customer = new Customer();

        customer.setIdCustomer(1L);
        customer.setNameCustomer("Jane Doe");
        customer.setAge(20);

        when(customerService.createCustomer(any(Customer.class))).thenReturn(customer);

        Customer result = customerController.addCustomer(customer);

        assertEquals("Jane Doe", result.getNameCustomer());
        verify(customerService, times(1)).createCustomer(any(Customer.class));
    }

    @Test
    void testUpdateCustomer() {
        Long customerId = 1L;
        Customer customer = new Customer();

        customer.setIdCustomer(customerId);
        customer.setNameCustomer("Jane Doe");
        customer.setAge(20);

        when(customerService.updateCustomer(any(Customer.class), eq(customerId))).thenReturn(customer);

        Customer result = customerController.updateCustomer(customer, customerId);

        assertEquals("Jane Doe", result.getNameCustomer());
        verify(customerService, times(1)).updateCustomer(any(Customer.class), eq(customerId));
    }

    @Test
    void testDeleteCustomer() {
        Long customerId = 1L;

        doNothing().when(customerService).deleteCustomer(customerId);

        customerController.deleteCustomer(customerId);

        verify(customerService, times(1)).deleteCustomer(customerId);
    }
}
