package com.example.demo.model;


import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long age;
    private String firstName;
    private String lastName;
    private Boolean student;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private List<Pet> pets;

    public Customer() {
    }

    public Customer(String firstName, String lastName, Long age, Boolean student) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.student = student;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, firstName='%s', lastName='%s', age='%d']",
                id, firstName, lastName, age);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(getAge(), customer.getAge()) &&
                Objects.equals(getFirstName(), customer.getFirstName()) &&
                Objects.equals(getLastName(), customer.getLastName()) &&
                Objects.equals(getStudent(), customer.getStudent());
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public Long getAge() {
        return age;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Boolean getStudent() {
        return student;
    }
}
