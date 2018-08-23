package com.example.demo;

import com.example.demo.repository.CustomerRepository;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ExampleRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CustomerRepository repository;

    @Test
    public void testExample() throws Exception {
//        {
//            Customer customer1 = new Customer("John", "Bauer", 12L);
//            ArrayList<Pet> pets = new ArrayList<>();
//            pets.add(new Pet("Boki", 130, "Dragon"));
//            pets.add(new Pet("Jack", 87, "Dog"));
//            customer1.setPets(pets);
//            this.entityManager.persist(customer1);
//        }
//        {
//            Customer customer2 = new Customer("Pete", "Doe", 14L);
//            ArrayList<Pet> pets = new ArrayList<>();
//            pets.add(new Pet("Boki", 130, "Dragon"));
//            pets.add(new Pet("Jack", 87, "Dog"));
//            customer2.setPets(pets);
//            this.entityManager.persist(customer2);
//        }
//        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
//        parameters.add("pets_weight-LOE","40");
//        parameters.add("pets_type","Dragon");
//        List<Customer> customers = this.repository.findAll(CustomerSpecificationFactory.findLightDragons(SearchCriteriaFactory.build()));
//        assertThat(customers.get(0).getFirstName()).isEqualTo("sboot");
    }

}