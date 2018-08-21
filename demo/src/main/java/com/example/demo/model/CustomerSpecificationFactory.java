package com.example.demo.model;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.*;
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
        // https://stackoverflow.com/a/4668015

        Specification<Customer> spec = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                select c.*
//                from customer c inner join pet p on p.customer_id = c.id
//                where p.type = 'Cat'
//                and p.name = 'Joki'

                Join<Customer, Pet> ys = root.join("pets");
                return criteriaBuilder.and(criteriaBuilder.equal(ys.get("type"), "Bird"), criteriaBuilder.equal(ys.get("name"), "Joki"));
            }

        };
        return spec;
    }

}
