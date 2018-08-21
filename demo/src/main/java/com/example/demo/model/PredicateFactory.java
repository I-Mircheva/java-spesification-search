package com.example.demo.model;


import com.querydsl.core.types.Predicate;
import org.springframework.util.MultiValueMap;

public class PredicateFactory {

    public static Predicate build(MultiValueMap<String, String> parameters) {
        SearchCriteriaFactory.build(parameters);
        return null;
    }
}
