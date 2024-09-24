package com.customer.api.service;

import com.customer.api.model.Customer;
import com.customer.api.model.ESCustomer;
import com.customer.api.repository.CustomerESRepository;
import com.customer.api.repository.CustomerRepository;
//import com.customer.api.repository.ESCustomerRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository customerRepository;

    private final CustomerESRepository esCustomerRepository;

    private final KafkaTemplate<String, Customer> kafkaTemplate;

    private String receiverMessage;

    private final RedisTemplate redisTemplate;

    private static final String KEY = "USER";

    public List<Customer> getAllCustomer(){
        return customerRepository.findAll();
    }

    public Customer createCustomer(Customer customer){
        Customer newCustomer = customerRepository.save(customer);
        //redis
        redisTemplate.opsForHash().put(KEY, newCustomer.getIdCustomer(), newCustomer);

        //elastic
        ESCustomer esCustomer = new ESCustomer(newCustomer.getIdCustomer(), newCustomer.getNameCustomer(), newCustomer.getGender(), newCustomer.getAge());
        esCustomerRepository.save(esCustomer);

        //kafka
        kafkaTemplate.send("kafka-customer", customer);
        return newCustomer;
    }

    //kafka
    @Async
    @KafkaListener(topics = "kafka-customer")
    public void listen(String message){
        receiverMessage = message;
        log.info(message);
    }

    public String getReceiverMessage(){
        return receiverMessage;
    }

    public Customer updateCustomer(Customer customer, Long id){
        Customer customerToUpdate = customerRepository.findById(id).get();
        customerToUpdate.setNameCustomer(customerToUpdate.getNameCustomer());
        customerToUpdate.setAge(customerToUpdate.getAge());
        customerToUpdate.setGender(customer.getGender());
        //redis
        redisTemplate.opsForHash().put(KEY, id, customerToUpdate);

        ESCustomer esCustomer = new ESCustomer(customerToUpdate.getIdCustomer(), customerToUpdate.getNameCustomer(), customerToUpdate.getGender(), customerToUpdate.getAge());
        esCustomerRepository.save(esCustomer);
        return customerRepository.save(customerToUpdate);
    }

    public void deleteCustomer(Long id){
        customerRepository.deleteById(id);
        redisTemplate.opsForHash().delete(KEY, id);
        esCustomerRepository.deleteById(id);
    }

    public List<Customer> getAllCustomerRedis(){
        List<Customer> list = redisTemplate.opsForHash().values(KEY);
        if(list.isEmpty()){
           return new ArrayList<Customer>();
        }
        return list;
    }

    public Iterable<ESCustomer> getAllESCustomer(){
        return esCustomerRepository.findAll();
    }

}
