package com.customer.api.repository;

import com.customer.api.model.ESCustomer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerESRepository extends ElasticsearchRepository<ESCustomer, Long> {
}
