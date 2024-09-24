package com.customer.api.controller;

import com.customer.api.model.Customer;
import com.customer.api.model.ESCustomer;
import com.customer.api.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomer();
    }

    //Redis
    @GetMapping("/getAllCustomerRedis")
    public List<Customer>getAllRedisCustomer(){return customerService.getAllCustomerRedis();}

    @GetMapping("/getAllESCustomer")
    public ResponseEntity<?> getAllESCustomerData() {
        Iterable<ESCustomer> customerList = customerService.getAllESCustomer();
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Customer loaded successfully");
        response.put("data", customerList);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/addCustomer")
    public Customer addCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @PutMapping("/updateCustomer")
    public Customer updateCustomer(@RequestBody Customer customer, @PathVariable Long id) {
        return customerService.updateCustomer(customer, id);
    }

    @DeleteMapping("/DeleteCustomer")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }

}
