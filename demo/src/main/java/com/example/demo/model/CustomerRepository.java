package com.example.demo.model;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.CrudRepository;

import java.util.*;

public interface CustomerRepository extends CrudRepository<Customer, Long>, QuerydslPredicateExecutor<Customer>,
        QuerydslBinderCustomizer<QCustomer>, JpaRepository<Customer, Long> {
//
//    List<Customer> findByAgeLessThanEqual(Long age);

    @Override
    default void customize(QuerydslBindings bindings, QCustomer customer) {
        bindings.bind(customer.id)
                .first(NumberExpression::goe);

        bindings.bind(Long.class).all( (NumberPath<Long> path, Collection<? extends Long> values) -> {

            BooleanBuilder predicate = new BooleanBuilder();

            for(String value : values) {
                if(value.startsWith("loe")) {
                    Long from = Long.parseLong(value.substring(3));
                    predicate.and(path.goe(from));
                } else if (value.startsWith("goe")) {
                    Long to = Long.parseLong(value.substring(3));
                    predicate.and(path.loe(to));
                } else {
                    Long exactly = Long.parseLong(value);
                    predicate.and(path.eq(exactly));
                }
            }
            return predicate;

//            List<? extends Long> ages = new ArrayList<Long>(values);
//            if (ages.size() == 1) {
//                        return Optional.of(predicate.or(path.eq(ages.get(0))));
//                    }
//            Long from = ages.get(0);
//            Long to = ages.get(1);
//            Optional.of(predicate.or(path.between(from,to)));
//            return Optional.of(predicate);

//                    List<? extends Long> ages = new ArrayList<>(values);
//                    if (ages.size() == 1) {
//                        return Optional.of(path.add(ages.get(0)));
//                    } else {
//                        Long from = ages.get(0);
//                        Long to = ages.get(1);
//
//                        if (from.equals(0)) {
//                            return predicate.or(path.loe(to));
//                        }
//                        if (to.equals(0)) {
//                            return predicate.or(path.goe(from));
//                        }
//
//                        return Optional.of(path.between(from, to));
//                    }
                });
        //bindings.excluding(customer.age);


//        bindings.bind(String.class).first((StringPath path, String value) -> {
//            if (value.startsWith("%") && value.endsWith("%")) {
//                return path.containsIgnoreCase(value.substring(1, value.length() - 1));
//            } else {
//                return path.eq(value);
//            }
//        });
    }
}
