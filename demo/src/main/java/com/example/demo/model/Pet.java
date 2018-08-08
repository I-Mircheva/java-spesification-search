package com.example.demo.model;

import javax.persistence.*;

@Entity
public class Pet {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private Integer weight;
    private String type;

    public Pet() {
    }

    public Pet(String name, Integer weight, String type) {
        this.name = name;
        this.weight = weight;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getWeight() {
        return weight;
    }

    public String getType() {
        return type;
    }
}
