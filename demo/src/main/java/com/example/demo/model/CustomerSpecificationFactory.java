package com.example.demo.model;

import org.springframework.data.jpa.domain.Specification;
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
        return null;
    }
}
