package com.example.demo.model;

import com.querydsl.core.types.Predicate;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.*;


public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long>, QueryDslPredicateExecutor<Customer> {
    List<Customer> findAll(Predicate predicate);
}