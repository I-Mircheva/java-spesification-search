package com.example.demo.model;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class CustomerSpecificationFactory {

    public static Specification<Customer> build(List<SpecSearchCriteria> params) {

        if (params.size() == 0)
            return null;

        Specification<Customer> result = new CustomerSpecification(params.get(0));

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result)
                    .and(new CustomerSpecification(params.get(i)));
        }
        return result;
    }

    public static Specification<Customer> findLightDragons() {
        // We have params send pets_weight-LOE=40 & pets_type="dragon"
//https://stackoverflow.com/a/4668015
        return (Specification<Customer>) (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(
                        root.join("pets").get("type"), "Cat"),
                criteriaBuilder.greaterThanOrEqualTo(
                        root.join("pets").get("name"), "Joki")
        );
    }
}
