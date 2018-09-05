package com.example.demo.repository;

import com.example.demo.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CustomerRepository extends JpaSpecificationExecutor<Customer>, PagingAndSortingRepository<Customer, Long> {

    Page<Customer> findAll(Pageable pageable);
}