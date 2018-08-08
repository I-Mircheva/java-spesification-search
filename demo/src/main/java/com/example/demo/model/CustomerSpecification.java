package com.example.demo.model;

import com.example.demo.model.Customer;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class CustomerSpecification implements Specification<Customer> {

	private SpecSearchCriteria criteria;

	public CustomerSpecification(final SpecSearchCriteria criteria) {
		super();
		this.criteria = criteria;
	}

	public SpecSearchCriteria getCriteria() {
		return criteria;
	}

	@Override
	public Predicate toPredicate(final Root<Customer> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {


		String[] levels = criteria.getKey().split("_");
		Path path = null;
		for(String level : levels) {
			if(path == null) {
				path = root.get(level);
			} else {
				path = path.get(level);
			}
		}

		switch (criteria.getOperation()) {
		case EQUALITY:
			return builder.equal(path, criteria.getValue());
		case NEGATION:
			return builder.notEqual(root.get(criteria.getKey()), criteria.getValue());
		case GREATER_THAN:
			return builder.greaterThan(root.get(criteria.getKey()), criteria.getValue().toString());
		case LESS_THAN:
			return builder.lessThan(root.get(criteria.getKey()), criteria.getValue().toString());
		case LIKE:
			return builder.like(root.get(criteria.getKey()), criteria.getValue().toString());
		case STARTS_WITH:
			return builder.like(root.get(criteria.getKey()), criteria.getValue() + "%");
		case ENDS_WITH:
			return builder.like(root.get(criteria.getKey()), "%" + criteria.getValue());
		case CONTAINS:
			return builder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
		default:
			return null;
		}
	}

}
