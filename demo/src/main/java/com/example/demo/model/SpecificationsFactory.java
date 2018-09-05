package com.example.demo.model;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.List;

public class SpecificationsFactory <Customer> {

    private Predicate buildSpec(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, List<SpecificationSearchCriteria> groupedSpecs) {

        Predicate result;
        Predicate resultFull = null;

        Path path;

        if(!groupedSpecs.get(0).getJoinPath().isEmpty()) {
            path = root.join(groupedSpecs.get(0).getJoinPath());
        } else {
            path = root;
        }
        try{
            for(SpecificationSearchCriteria criteria : groupedSpecs) {
                switch (criteria.getOperation()) {
                    case EQUALITY:
                        result = criteriaBuilder.equal(path.get(criteria.getKey()), criteria.getValue());
                        break;
                    case GREATER_THAN:
                        result = criteriaBuilder.greaterThan(path.get(criteria.getKey()), criteria.getValue().toString());
                        break;
                    case GREATER_OR_EQUALS:
                        result = criteriaBuilder.greaterThanOrEqualTo(path.get(criteria.getKey()), criteria.getValue().toString());
                        break;
                    case LESS_OR_EQUALS:
                        result = criteriaBuilder.lessThanOrEqualTo(path.get(criteria.getKey()), criteria.getValue().toString());
                        break;
                    case LESS_THAN:
                        result = criteriaBuilder.lessThan(path.get(criteria.getKey()), criteria.getValue().toString());
                        break;
                    default:
                        result = null;
                }
                if(resultFull == null) {
                    resultFull = result;
                } else {
                    resultFull = criteriaBuilder.and(resultFull,result);
                }
            }
        } catch (IllegalArgumentException eIA  ) {
            throw new BadQueryParameterException("This parameter does not exist!");
        }
        query.distinct(true);

        return resultFull;

    }

    public Specification<Customer> groupSpec(List<SpecificationSearchCriteria> groupedSpecs) {
            return (root, query, criteriaBuilder) -> buildSpec(root, query, criteriaBuilder, groupedSpecs);

    }
}