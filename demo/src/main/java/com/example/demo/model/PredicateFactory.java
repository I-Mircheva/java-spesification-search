package com.example.demo.model;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.*;

import java.util.stream.Collectors;

public class PredicateFactory {


    private static final Map<String, SearchOperation> operations = new HashMap<>();

    static {
        for (SearchOperation singleOperation : SearchOperation.values()) {
            operations.put(singleOperation.getMarker(), singleOperation);
        }
    }

    public static Specification<Customer> build(MultiValueMap<String, String> parameters){

        Collection<List<SpecSearchCriteria>> one = grouping(parameters).values();
        Specification<Customer> result = groupSpec(one.iterator().next());

        for(List<SpecSearchCriteria> singleGroupSpec : one) {
            result = Specification.where(result)
                    .and(groupSpec(singleGroupSpec));
        }

        return result;
    }


    public static Specification<Customer> groupSpec(List<SpecSearchCriteria> groupedSpecs) {
        return (Specification<Customer>) (root, query, criteriaBuilder) -> buildSpec(root, query, criteriaBuilder, groupedSpecs);
    }

    private static Predicate buildSpec(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder, List<SpecSearchCriteria> groupedSpecs) {

        Predicate result = null;
        Predicate resultFull = null;

        Path path;

        if(!groupedSpecs.get(0).getJoinPath().isEmpty())
            path = root.join(groupedSpecs.get(0).getJoinPath());
        else
            path = root;

        for(SpecSearchCriteria one : groupedSpecs) {
            
            switch (one.getOperation()) {
                case EQUALITY:
                    result = criteriaBuilder.equal(path.get(one.getKey()), one.getValue());
                    break;
                case GREATER_THAN:
                    result = criteriaBuilder.greaterThan(path.get(one.getKey()), one.getValue().toString());
                    break;
                case GREATER_OR_EQUALS:
                    result = criteriaBuilder.greaterThanOrEqualTo(path.get(one.getKey()), one.getValue().toString());
                    break;
                case LESS_OR_EQUALS:
                    result = criteriaBuilder.lessThanOrEqualTo(path.get(one.getKey()), one.getValue().toString());
                    break;
                case LESS_THAN:
                    result = criteriaBuilder.lessThan(path.get(one.getKey()), one.getValue().toString());
                    break;
                default:
                    result = null;
            }

            resultFull = criteriaBuilder.and(resultFull,result);
        }
        return resultFull;

    }


    public static Map<String, List<SpecSearchCriteria>> grouping(MultiValueMap<String, String> parameters){
        List<SpecSearchCriteria> readySpecs = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : parameters.entrySet()) {

            // [ pets.name-EQ ] becomes (hopefully) [ pets.name ] [ EQ ]
            String[] pathKeyAndOperation = entry.getKey().split("-");

            // [ pets.name ] becomes (hopefully) [ pets ] [ name ]
            List<String> list = new ArrayList<>(Arrays.asList(pathKeyAndOperation[0].split("\\.")));

            int indexOfKey = list.size() - 1;
            // key is [ name ]
            String key = list.remove(indexOfKey);

            // Join the path to use later with "." delimiter
            String path = String.join(".", list);

            System.out.println(pathKeyAndOperation.length);

            SpecSearchCriteria singleSpec = new SpecSearchCriteria(path, key, operations.get(pathKeyAndOperation[1]), entry.getValue());
            readySpecs.add(singleSpec);
        }

        Map<String, List<SpecSearchCriteria>> searchCriteriaListGroupedByPath = readySpecs.stream().collect(
                Collectors.groupingBy(SpecSearchCriteria::getJoinPath)
        );
        return searchCriteriaListGroupedByPath;
    }
}






































