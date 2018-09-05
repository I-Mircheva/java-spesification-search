package com.example.demo;

import com.example.demo.model.Customer;
import com.example.demo.model.Pet;
import com.example.demo.repository.CustomerRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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


    private void run(List<Customer> resultList, String s) throws Exception{
        String response = this.mvc.perform(get(s)).andReturn().getResponse().getContentAsString();
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Customer>>() {}.getType();
        List<Customer> responseList = gson.fromJson(response, listType);

        for (Customer c : resultList) {
            Assert.assertTrue(responseList.contains(c));
        }
        Assert.assertEquals(responseList.size(), resultList.size());
    }


    @Test
    public void exampleSearchNothing() throws Exception {
        repository.deleteAll();
        List<Customer> resultList = new ArrayList<>();
        {
            Customer customer1 = new Customer("Jack", "Bauer", 12L,true);
            ArrayList<Pet> pets = new ArrayList<>();
            pets.add(new Pet("Bok", 130, "Dragon"));
            pets.add(new Pet("Jack", 87, "Dog"));
            customer1.setPets(pets);
            repository.save(customer1);
            resultList.add(customer1);
        }

        {
            Customer customer2 = new Customer("Chloe", "Brian", 13L,true);
            ArrayList<Pet> pets = new ArrayList<>();
            pets.add(new Pet("Joki", 10, "Snake"));
            pets.add(new Pet("Sack", 77, "Cat"));
            customer2.setPets(pets);
            repository.save(customer2);
            resultList.add(customer2);
        }

        {
            Customer customer3 = new Customer("Kim", "Bauer", 10L,true);
            ArrayList<Pet> pets = new ArrayList<>();
            pets.add(new Pet("Joki", 1, "Bird"));
            pets.add(new Pet("Joki", 3, "Leopard"));
            pets.add(new Pet("Sack", 5, "Cat"));
            customer3.setPets(pets);
            repository.save(customer3);
            resultList.add(customer3);
        }

        {
            Customer customer4 = new Customer("David", "Palmer", 5L,true);
            ArrayList<Pet> pets = new ArrayList<>();
            pets.add(new Pet("Beauty", 1, "Caterpillar"));
            pets.add(new Pet("Ugly", 2, "Butterfly"));
            customer4.setPets(pets);
            repository.save(customer4);
            resultList.add(customer4);
        }

        {
            Customer customer5 = new Customer("Michelle", "Dessler", 30L,true);
            ArrayList<Pet> pets = new ArrayList<>();
            pets.add(new Pet("Jaki", 10, "Snake"));
            pets.add(new Pet("Sack", 77, "Cat"));
            customer5.setPets(pets);
            repository.save(customer5);
            resultList.add(customer5);
        }
        run(resultList,"/customers");

    }

    @Test
    public void exampleSearchOnlyPaging() throws Exception {
        repository.deleteAll();

        List<Customer> resultList = new ArrayList<>();
        {
            Customer customer1 = new Customer("Jack", "Bauer", 12L,true);
            ArrayList<Pet> pets = new ArrayList<>();
            pets.add(new Pet("Bok", 130, "Dragon"));
            pets.add(new Pet("Jack", 87, "Dog"));
            customer1.setPets(pets);
            repository.save(customer1);
            resultList.add(customer1);
        }

        {
            Customer customer2 = new Customer("Chloe", "Brian", 13L,true);
            ArrayList<Pet> pets = new ArrayList<>();
            pets.add(new Pet("Joki", 10, "Snake"));
            pets.add(new Pet("Sack", 77, "Cat"));
            customer2.setPets(pets);
            repository.save(customer2);
            resultList.add(customer2);
        }

        run(resultList,"/customers?page=0&size=2");

    }

    @Test
    public void exampleSearchCustomersByAge() throws Exception {
        repository.deleteAll();
        List<Customer> resultList = new ArrayList<>();

        {
            Customer customer4 = new Customer("David", "Palmer", 5L,true);
            ArrayList<Pet> pets = new ArrayList<>();
            pets.add(new Pet("Beauty", 1, "Caterpillar"));
            pets.add(new Pet("Ugly", 2, "Butterfly"));
            customer4.setPets(pets);
            repository.save(customer4);
            resultList.add(customer4);
        }

        {
            Customer customer5 = new Customer("Michelle", "Dessler", 30L,true);
            ArrayList<Pet> pets = new ArrayList<>();
            pets.add(new Pet("Jaki", 10, "Snake"));
            pets.add(new Pet("Sack", 77, "Cat"));
            customer5.setPets(pets);
            repository.save(customer5);
        }
        run(resultList,"/customers?age-LT=24");

    }

    @Test
    public void exampleSearchPetsByWeight() throws Exception {
        repository.deleteAll();
        List<Customer> resultList = new ArrayList<>();

        {
            Customer customer1 = new Customer("Jacky", "Bauer", 12L,true);
            ArrayList<Pet> pets = new ArrayList<>();
            pets.add(new Pet("Boki", 130, "Dragon"));
            pets.add(new Pet("Jack", 87, "Dog"));
            customer1.setPets(pets);
            repository.save(customer1);
            resultList.add(customer1);

        }

        {
            Customer customer2 = new Customer("Chloen", "Brian", 13L,true);
            ArrayList<Pet> pets = new ArrayList<>();
            pets.add(new Pet("Joki", 10, "Snake"));
            pets.add(new Pet("Sack", 77, "Cat"));
            customer2.setPets(pets);
            repository.save(customer2);
        }
        run(resultList,"/customers?pets.weight-GOE=78");

    }

    @Test
    public void exampleSearchPetsByPetParamsAndPaging() throws Exception {
        repository.deleteAll();
        List<Customer> resultList = new ArrayList<>();
        List<Customer> resultList2 = new ArrayList<>();

        {
            Customer customer1 = new Customer("Jack", "Bauer", 12L,true);
            ArrayList<Pet> pets = new ArrayList<>();
            pets.add(new Pet("Boki", 130, "Dragon"));
            pets.add(new Pet("Joe", 87, "Dog"));
            customer1.setPets(pets);
            repository.save(customer1);
        }

        {
            Customer customer2 = new Customer("Chloe", "Brian", 13L,true);
            ArrayList<Pet> pets = new ArrayList<>();
            pets.add(new Pet("Joki", 10, "Developer"));
            pets.add(new Pet("Sack", 77, "Cat"));
            customer2.setPets(pets);
            repository.save(customer2);
            resultList.add(customer2);
        }

        run(resultList,"/customers?pets.weight-LT=87&page=0&size=1");
        run(resultList2,"/customers?pets.weight-LT=87&page=1&size=1");

    }

    @Test
    public void exampleSearchPetsByPetParamsCustomerParamsAndPaging() throws Exception {
        repository.deleteAll();
        List<Customer> resultList = new ArrayList<>();
        List<Customer> resultList2 = new ArrayList<>();

        {
            Customer customer1 = new Customer("Jacky", "Bauer", 12L,true);
            ArrayList<Pet> pets = new ArrayList<>();
            pets.add(new Pet("Boki", 130, "Dragon"));
            pets.add(new Pet("Joe", 87, "Dog"));
            customer1.setPets(pets);
            repository.save(customer1);
        }

        {
            Customer customer2 = new Customer("Chloe", "Brian", 13L,true);
            ArrayList<Pet> pets = new ArrayList<>();
            pets.add(new Pet("Joki", 10, "Developer"));
            pets.add(new Pet("Sacky", 77, "Cat"));
            customer2.setPets(pets);
            repository.save(customer2);
            resultList.add(customer2);
        }

        run(resultList,"/customers?pets.weight-LT=87&page=0&size=1&age=13");
        run(resultList2,"/customers?pets.weight-LT=87&page=1&size=1");

    }

    @Test
    public void exampleSearchDuplicatingParamsForPets() throws Exception {
        repository.deleteAll();
        List<Customer> resultList = new ArrayList<>();

        {
            Customer customer1 = new Customer("Jacky", "Bauer", 12L,true);
            ArrayList<Pet> pets = new ArrayList<>();
            pets.add(new Pet("Boki", 130, "Dragon"));
            pets.add(new Pet("Jack", 87, "Dog"));
            customer1.setPets(pets);
            repository.save(customer1);

        }

        {
            Customer customer2 = new Customer("Chloen", "Brian", 13L,true);
            ArrayList<Pet> pets = new ArrayList<>();
            pets.add(new Pet("Joki", 10, "Snake"));
            pets.add(new Pet("Sack", 77, "Cat"));
            customer2.setPets(pets);
            repository.save(customer2);
        }
        run(resultList,"/customers?pets.weight-LOE=77&pets.weight-GOE=89");

    }

    @Test
    public void exampleSearchDuplicatingParamsForCustomers() throws Exception {
        repository.deleteAll();
        List<Customer> resultList = new ArrayList<>();

        {
            Customer customer1 = new Customer("Jacky", "Bauer", 12L,true);
            ArrayList<Pet> pets = new ArrayList<>();
            pets.add(new Pet("Boki", 130, "Dragon"));
            pets.add(new Pet("Jack", 87, "Dog"));
            customer1.setPets(pets);
            repository.save(customer1);
            resultList.add(customer1);

        }

        {
            Customer customer2 = new Customer("Chloen", "Brian", 13L,true);
            ArrayList<Pet> pets = new ArrayList<>();
            pets.add(new Pet("Joki", 10, "Snake"));
            pets.add(new Pet("Sack", 77, "Cat"));
            customer2.setPets(pets);
            repository.save(customer2);
            resultList.add(customer2);

        }
        run(resultList,"/customers?age-LOE=29&age-LOE=85");

    }

    @Test
    public void exampleSearchDuplicatingParamsForPetsAndCust() throws Exception {
        repository.deleteAll();
        List<Customer> resultList = new ArrayList<>();

        {
            Customer customer1 = new Customer("Jacky", "Bauer", 12L,true);
            ArrayList<Pet> pets = new ArrayList<>();
            pets.add(new Pet("Boki", 130, "Dragon"));
            pets.add(new Pet("Joe", 87, "Dog"));
            customer1.setPets(pets);
            repository.save(customer1);
        }

        {
            Customer customer2 = new Customer("Chloe", "Brian", 13L,true);
            ArrayList<Pet> pets = new ArrayList<>();
            pets.add(new Pet("Joki", 10, "Developer"));
            pets.add(new Pet("Sacky", 77, "Cat"));
            customer2.setPets(pets);
            repository.save(customer2);
        }

        run(resultList,"/customers?pets.weight-LT=87&pets.weight-GOE=87&age=13&age=12");

    }

}