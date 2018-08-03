package com.example.demo.controller;

import com.example.demo.model.Customer;
import com.example.demo.model.CustomerRepository;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.PostConstruct;


@RestController
public class CustomerController {

    @Autowired
    CustomerRepository repository;

    @RequestMapping(method = RequestMethod.GET, value = "/customer")
    public Iterable<Customer> find(@QuerydslPredicate(root = Customer.class) Predicate predicate, Pageable pageable,
                                   @RequestParam MultiValueMap<String, String> parameters) {

        System.out.println();
        System.out.println(predicate);
        System.out.println();

        return repository.findAll(predicate, pageable);
    }

    @PostConstruct
    public void prepareDB() {
        repository.save(new Customer("Jack", "Bauer"));
        repository.save(new Customer("Chloe", "O'Brian"));
        repository.save(new Customer("Kim", "Bauer"));
        repository.save(new Customer("David", "Palmer"));
        repository.save(new Customer("Michelle", "Dessler"));
    }

}
