package com.example.demo.controller;

import com.example.demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.*;

@RestController
public class CustomerController {

    @Autowired
    CustomerRepository repository;

    @RequestMapping(method = RequestMethod.GET, value = "/customers")
    @ResponseBody
    public List<Customer> findAllBySpecification(@RequestParam MultiValueMap<String, String> parameters, Pageable pageable) {
        return repository.findAll(PredicateFactory.build(parameters), pageable).getContent(); // TODO Pageable
//                CustomerSpecificationFactory.build(SearchCriteriaFactory.build(parameters)));
    }

    @PostConstruct
    public void prepareDB() {
        repository.deleteAll();

        {
            Customer customer1 = new Customer("Jack", "Bauer", 12L);
            ArrayList<Pet> pets = new ArrayList<>();
            pets.add(new Pet("Boki", 130, "Dragon"));
            pets.add(new Pet("Jack", 87, "Dog"));
            customer1.setPets(pets);
            repository.save(customer1);
        }

        {
            Customer customer2 = new Customer("Chloe", "Brian", 13L);
            ArrayList<Pet> pets = new ArrayList<>();
            pets.add(new Pet("Joki", 10, "Snake"));
            pets.add(new Pet("Sack", 77, "Cat"));
            customer2.setPets(pets);
            repository.save(customer2);
        }

        {
            Customer customer3 = new Customer("Kim", "Bauer", 10L);
            ArrayList<Pet> pets = new ArrayList<>();
            pets.add(new Pet("Joki", 1, "Bird"));
            pets.add(new Pet("Joki", 3, "Leopard"));
            pets.add(new Pet("Sack", 5, "Cat"));
            customer3.setPets(pets);
            repository.save(customer3);
        }

        {
            Customer customer4 = new Customer("David", "Palmer", 5L);
            ArrayList<Pet> pets = new ArrayList<>();
            pets.add(new Pet("Beauty", 1, "Caterpillar"));
            pets.add(new Pet("Ugly", 2, "Butterfly"));
            customer4.setPets(pets);
            repository.save(customer4);
        }

        {
            Customer customer5 = new Customer("Michelle", "Dessler", 30L);
            ArrayList<Pet> pets = new ArrayList<>();
            pets.add(new Pet("Jaki", 10, "Snake"));
            pets.add(new Pet("Sack", 77, "Cat"));
            customer5.setPets(pets);
            repository.save(customer5);
        }
    }
}
