package com.customer.api.service;

import com.customer.api.model.Customer;
import com.customer.api.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<Customer> getAllCustomer(){
        return customerRepository.findAll();
    }

    public Customer createCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Customer customer, Long id){
        Customer customerToUpdate = customerRepository.findById(id).get();
        customerToUpdate.setNameCustomer(customerToUpdate.getNameCustomer());
        customerToUpdate.setAge(customerToUpdate.getAge());
        customerToUpdate.setGender(customer.getGender());
        return customerRepository.save(customerToUpdate);
    }

    public void deleteCustomer(Long id){
        customerRepository.deleteById(id);
    }
}
