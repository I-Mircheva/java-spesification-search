package com.example.demo.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long>, QuerydslPredicateExecutor<Customer>,
        QuerydslBinderCustomizer<QCustomer>, JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    StringBuffer option = new StringBuffer();


    @Override
    default void customize(QuerydslBindings bindings, QCustomer customer) {

        //        bindings.bind(customer.id)
        //                .first(NumberExpression::goe);


//        bindings.bind(String.class).first((StringPath path, String value) -> {
//            if (value.startsWith("%") && value.endsWith("%")) {
//                return path.containsIgnoreCase(value.substring(1, value.length() - 1));
//            } else {
//                return path.eq(value);
//            }
//        });

        //bindings.excluding(customer.email); // Example
//
//        bindings.bind(Long.class).all( (NumberPath<Long> path, Collection<? extends Long> values) -> {
//
//            BooleanBuilder predicate = new BooleanBuilder();
//            List<? extends Long> variable = new ArrayList<Long>(values);
//
//                if (variable.size() == 1) {
//                    return Optional.of(predicate.or(path.eq(variable.get(0))));
//                }
//                Long from = variable.get(0);
//                Long to = variable.get(1);
//                Optional.of(predicate.or(path.between(from,to)));
//                return Optional.of(predicate);
//        });
    }
    Page<Customer> findAll(Pageable pageable);
}