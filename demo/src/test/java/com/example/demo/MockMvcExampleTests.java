package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MockMvcExampleTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void exampleSearchPetsByWeight() throws Exception {
        this.mvc.perform(get("/customers?pets.weight=3")).andExpect(status().isOk())
                .andExpect(content().string(
                        "[{\"id\":1,\"age\":12,\"firstName\":\"Jack\",\"lastName\":\"Bauer\",\"pets\":[{\"id\":2,\"name\":\"Boki\",\"weight\":130,\"type\":\"Dragon\"},{\"id\":3,\"name\":\"Jack\",\"weight\":87,\"type\":\"Dog\"},{\"id\":4,\"name\":\"Jacob\",\"weight\":13,\"type\":\"Cat\"},{\"id\":5,\"name\":\"Koko\",\"weight\":10,\"type\":\"Mouse\"},{\"id\":6,\"name\":\"Sony\",\"weight\":9,\"type\":\"Snake\"}]}]"));
    }
    @Test
    public void exampleSearchPetsByWeightAndCustomerByName() throws Exception {
        this.mvc.perform(get("/customers?firstName=Jack&pets.weight=9")).andExpect(status().isOk())
                .andExpect(content().string(
                        "[{\"id\":1,\"age\":12,\"firstName\":\"Jack\",\"lastName\":\"Bauer\",\"pets\":[{\"id\":2,\"name\":\"Boki\",\"weight\":130,\"type\":\"Dragon\"},{\"id\":3,\"name\":\"Jack\",\"weight\":87,\"type\":\"Dog\"},{\"id\":4,\"name\":\"Jacob\",\"weight\":13,\"type\":\"Cat\"},{\"id\":5,\"name\":\"Koko\",\"weight\":10,\"type\":\"Mouse\"},{\"id\":6,\"name\":\"Sony\",\"weight\":9,\"type\":\"Snake\"}]}]"));
    }


}