package com.example.demo.controller;

import com.example.demo.model.*;
import com.google.common.base.Joiner;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class CustomerController {

    @Autowired
    CustomerRepository repository;

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    @ResponseBody
    public List<Customer> findAllBySpecification(@RequestParam(value = "search") String search) {
        CustomerSpecification builder = new CustomerSpecification();
        String operationSetExper = Joiner.on("|").join(SearchOperation.SIMPLE_OPERATION_SET);
        Pattern pattern = Pattern.compile(
                "(\\w+?)(" + operationSetExper + ")(\p{Punct}?)(\\w+?)(\p{Punct}?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(
                    matcher.group(1),
                    matcher.group(2),
                    matcher.group(4),
                    matcher.group(3),
                    matcher.group(5));
        }

        Specification<Customer> spec = builder.build();
        return dao.findAll(spec);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/customers")
    public Iterable<Customer> find(@QuerydslPredicate(root = Customer.class) Predicate predicate, Pageable pageable,
                                   @RequestParam MultiValueMap<String, String> parameters) {

        CustomerRepository.readParams(parameters);
        return repository.findAll(predicate, pageable);
    }

    @PostConstruct
    public void prepareDB() {

        repository.deleteAll();

        Customer customer1 = new Customer("Jack", "Bauer", 12L);
        Customer customer2 = new Customer("Chloe", "Brian", 13L);
        Customer customer3 = new Customer("Kim", "Bauer", 10L);
        Customer customer4 = new Customer("David", "Palmer", 5L);
        Customer customer5 = new Customer("Michelle", "Dessler", 30L);

        ArrayList<Pet> pets = new ArrayList<>();

        Pet pet1 = new Pet("Boki", 130, "Dragon");
        Pet pet2 = new Pet("Jack", 87, "Dog");
        Pet pet3 = new Pet("Jacob", 13, "Cat");
        Pet pet4 = new Pet("Koko", 10, "Mouse");
        Pet pet5 = new Pet("Sony", 9, "Snake");


        pets.add(pet1);
        pets.add(pet2);
        pets.add(pet3);
        pets.add(pet4);
        pets.add(pet5);

        customer1.setPets(pets);
//        customer2.setPets(pets);
//        customer3.setPets(pets);
//        customer4.setPets(pets);
//        customer5.setPets(pets);


        repository.save(customer1);
        repository.save(customer2);
        repository.save(customer3);
        repository.save(customer4);
        repository.save(customer5);
    }

}
