package com.example.demo.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerRepository extends JpaSpecificationExecutor<Customer>, PagingAndSortingRepository<Customer, Long> {

    Page<Customer> findAll(Pageable pageable);
}