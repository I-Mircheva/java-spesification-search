package com.example.demo.model;

import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long>, QuerydslPredicateExecutor<Customer>,
        QuerydslBinderCustomizer<QCustomer> {

    @Override
    default void customize(QuerydslBindings bindings, QCustomer customer) {

//        bindings.bind(customer.firstName).first(StringExpression::containsIgnoreCase);

        bindings
                .bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);

        bindings
                .bind(Long.class)
                .first((SingleValueBinding<NumberPath<Long>, Long>) NumberExpression::goe);

//        bindings.excluding(customer.lastName);
    }
}
