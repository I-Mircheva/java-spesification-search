package com.example.demo;

import com.example.demo.model.Customer;
import com.example.demo.model.Pet;
import com.example.demo.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MockMvcExampleTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    CustomerRepository repository;


    @Test
    public void exampleSearchPetsByWeight() throws Exception {
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
        this.mvc.perform(get("/customers?pets.weight-GOE=78")).andExpect(status().isOk())
                .andExpect(content().string(
                        "[{\"id\":1,\"age\":12,\"firstName\":\"Jack\",\"lastName\":\"Bauer\",\"pets\":[{\"id\":2,\"name\":\"Boki\",\"weight\":130,\"type\":\"Dragon\"},{\"id\":3,\"name\":\"Jack\",\"weight\":87,\"type\":\"Dog\"}]}]"));
    }

    @Test
    public void exampleSearchPetsByPetParamsAndPaging() throws Exception {
        {
            Customer customer1 = new Customer("Jack", "Bauer", 12L);
            ArrayList<Pet> pets = new ArrayList<>();
            pets.add(new Pet("Boki", 130, "Dragon"));
            pets.add(new Pet("Joe", 87, "Dog"));
            customer1.setPets(pets);
            repository.save(customer1);
        }

        {
            Customer customer2 = new Customer("Chloe", "Brian", 13L);
            ArrayList<Pet> pets = new ArrayList<>();
            pets.add(new Pet("Joki", 10, "Developer"));
            pets.add(new Pet("Sack", 77, "Cat"));
            customer2.setPets(pets);
            repository.save(customer2);
        }
        this.mvc.perform(get("/customers?pets.weight-LT=87&page=0&size=1")).andExpect(status().isOk())
                .andExpect(content().string(
                        "[{\"id\":4,\"age\":13,\"firstName\":\"Chloe\",\"lastName\":\"Brian\",\"pets\":[{\"id\":5,\"name\":\"Joki\",\"weight\":10,\"type\":\"Developer\"},{\"id\":6,\"name\":\"Sack\",\"weight\":77,\"type\":\"Cat\"}]}]"));
        this.mvc.perform(get("/customers?pets.weight-LT=87&page=1&size=1")).andExpect(status().isOk())
                .andExpect(content().string(
                        "[]"));
    }

    @Test
    public void exampleSearchPetsByPetParamsCustomerParamsAndPaging() throws Exception {
        {
            Customer customer1 = new Customer("Jacky", "Bauer", 12L);
            ArrayList<Pet> pets = new ArrayList<>();
            pets.add(new Pet("Boki", 130, "Dragon"));
            pets.add(new Pet("Joe", 87, "Dog"));
            customer1.setPets(pets);
            repository.save(customer1);
        }

        {
            Customer customer2 = new Customer("Chloe", "Brian", 13L);
            ArrayList<Pet> pets = new ArrayList<>();
            pets.add(new Pet("Joki", 10, "Developer"));
            pets.add(new Pet("Sacky", 77, "Cat"));
            customer2.setPets(pets);
            repository.save(customer2);
        }
        this.mvc.perform(get("/customers?pets.weight-LT=87&page=0&size=1&age=13")).andExpect(status().isOk())
                .andExpect(content().string(
                        "[{\"id\":4,\"age\":13,\"firstName\":\"Chloe\",\"lastName\":\"Brian\",\"pets\":[{\"id\":5,\"name\":\"Joki\",\"weight\":10,\"type\":\"Developer\"},{\"id\":6,\"name\":\"Sack\",\"weight\":77,\"type\":\"Cat\"}]}]"));
        this.mvc.perform(get("/customers?pets.weight-LT=87&page=1&size=1")).andExpect(status().isOk())
                .andExpect(content().string(
                        "[]"));
    }




}