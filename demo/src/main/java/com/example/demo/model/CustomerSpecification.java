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
		for(int i = 0; i < levels.length; i++) {
			if(path == null) {
				if(i == levels.length-1){
					path = root.get(levels[i]);
				} else {
					path = root.join(levels[i]);
				}
				continue;
			}
			if (i == levels.length - 1) {
				path = path.get(levels[i]);
			}else {
				path = ((From)path).join(levels[i]);
			}
		}

		Predicate result;

		switch (criteria.getOperation()) {
		case EQUALITY:
			result = builder.equal(path, criteria.getValue());
			break;
		case NEGATION:
			result = builder.notEqual(path, criteria.getValue());
			break;
		case GREATER_THAN:
			result = builder.greaterThan(path, criteria.getValue().toString());
			break;
		case GREATER_OR_EQUALS:
			result = builder.greaterThanOrEqualTo(path, criteria.getValue().toString());
			break;
		case LESS_THAN:
			result = builder.lessThan(path, criteria.getValue().toString());
			break;
		case LIKE:
			result = builder.like(path, criteria.getValue().toString());
			break;
		case STARTS_WITH:
			result = builder.like(path, criteria.getValue() + "%");
			break;
		case ENDS_WITH:
			result = builder.like(path, "%" + criteria.getValue());
			break;
		case CONTAINS:
			result = builder.like(path, "%" + criteria.getValue() + "%");
			break;
		default:
			result = null;
		}

		query.distinct(true); // Remove duplicates

		return result;

	}

}
