package com.example.demo.controller;

import com.example.demo.factory.PredicateFactory;
import com.example.demo.model.*;
import com.example.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CustomerController {

    @Autowired
    CustomerRepository repository;

    @RequestMapping(method = RequestMethod.GET, value = "/customers")
    @ResponseBody
    public List<Customer> findAllBySpecification(@RequestParam Map<String, String> parameters, Pageable pageable) {
        try{
            return repository.findAll(PredicateFactory.build(parameters), pageable).getContent();
        } catch (
        InvalidDataAccessApiUsageException e) {
            throw new BadQueryParameterException("This parameter does not exist!");
        }
    }


}
