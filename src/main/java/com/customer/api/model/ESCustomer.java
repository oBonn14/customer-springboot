package com.customer.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "customer")
@NoArgsConstructor
@AllArgsConstructor
public class ESCustomer {
    @Id
    private Long customerId;
    private String nameCustomer;
    private int age;
    private int gender;
}
