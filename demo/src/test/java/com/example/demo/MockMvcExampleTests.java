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
    public void exampleSearchNothing() throws Exception {
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
        this.mvc.perform(get("/customers")).andExpect(status().isOk())
                .andExpect(content().string(
                        "[{\"id\":1,\"age\":12,\"firstName\":\"Jack\",\"lastName\":\"Bauer\",\"pets\":[{\"id\":2,\"name\":\"Boki\",\"weight\":130,\"type\":\"Dragon\"},{\"id\":3,\"name\":\"Jack\",\"weight\":87,\"type\":\"Dog\"}]},{\"id\":7,\"age\":10,\"firstName\":\"Kim\",\"lastName\":\"Bauer\",\"pets\":[{\"id\":8,\"name\":\"Joki\",\"weight\":1,\"type\":\"Bird\"},{\"id\":9,\"name\":\"Joki\",\"weight\":3,\"type\":\"Leopard\"},{\"id\":10,\"name\":\"Sack\",\"weight\":5,\"type\":\"Cat\"}]},{\"id\":14,\"age\":30,\"firstName\":\"Michelle\",\"lastName\":\"Dessler\",\"pets\":[{\"id\":15,\"name\":\"Jaki\",\"weight\":10,\"type\":\"Snake\"},{\"id\":16,\"name\":\"Sack\",\"weight\":77,\"type\":\"Cat\"}]},{\"id\":4,\"age\":13,\"firstName\":\"Chloe\",\"lastName\":\"Brian\",\"pets\":[{\"id\":5,\"name\":\"Joki\",\"weight\":10,\"type\":\"Snake\"},{\"id\":6,\"name\":\"Sack\",\"weight\":77,\"type\":\"Cat\"}]},{\"id\":11,\"age\":5,\"firstName\":\"David\",\"lastName\":\"Palmer\",\"pets\":[{\"id\":12,\"name\":\"Beauty\",\"weight\":1,\"type\":\"Caterpillar\"},{\"id\":13,\"name\":\"Ugly\",\"weight\":2,\"type\":\"Butterfly\"}]}]"));
    }

    @Test
    public void exampleSearchOnlyPaging() throws Exception {
        repository.deleteAll();

        this.mvc.perform(get("/customers?page=0&size=2")).andExpect(status().isOk())
                .andExpect(content().string(
                        "[{\"id\":1,\"age\":12,\"firstName\":\"Jack\",\"lastName\":\"Bauer\",\"pets\":[{\"id\":2,\"name\":\"Boki\",\"weight\":130,\"type\":\"Dragon\"},{\"id\":3,\"name\":\"Jack\",\"weight\":87,\"type\":\"Dog\"}]},{\"id\":4,\"age\":13,\"firstName\":\"Chloe\",\"lastName\":\"Brian\",\"pets\":[{\"id\":5,\"name\":\"Joki\",\"weight\":10,\"type\":\"Snake\"},{\"id\":6,\"name\":\"Sack\",\"weight\":77,\"type\":\"Cat\"}]}]"));
    }

    @Test
    public void exampleSearchPetsByWeight() throws Exception {
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
        this.mvc.perform(get("/customers?pets.weight-GOE=78")).andExpect(status().isOk())
                .andExpect(content().string(
                        "[{\"id\":1,\"age\":12,\"firstName\":\"Jack\",\"lastName\":\"Bauer\",\"pets\":[{\"id\":2,\"name\":\"Boki\",\"weight\":130,\"type\":\"Dragon\"},{\"id\":3,\"name\":\"Jack\",\"weight\":87,\"type\":\"Dog\"}]}]"));
    }

    @Test
    public void exampleSearchPetsByPetParamsAndPaging() throws Exception {
        repository.deleteAll();

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
        repository.deleteAll();

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
                        "[{\"id\":4,\"age\":13,\"firstName\":\"Chloe\",\"lastName\":\"Brian\",\"pets\":[{\"id\":5,\"name\":\"Joki\",\"weight\":10,\"type\":\"Developer\"},{\"id\":6,\"name\":\"Sacky\",\"weight\":77,\"type\":\"Cat\"}]}]"));
        this.mvc.perform(get("/customers?pets.weight-LT=87&page=1&size=1")).andExpect(status().isOk())
                .andExpect(content().string(
                        "[]"));
    }




}