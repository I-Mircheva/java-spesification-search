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
        Path path = root.join(groupedSpecs.get(0).getJoinPath());

        for(SpecSearchCriteria one : groupedSpecs) {

            Join<Customer, Pet> ys = root.join("pets");
            //            return criteriaBuilder.and(criteriaBuilder.equal(ys.get("type"), "Bird"), criteriaBuilder.equal(ys.get("name"), "Joki"));

            Subquery<Pet> subquery = query.subquery(Pet.class);
            Root<Pet> subqueryRoot = subquery.from(Pet.class);

            subquery.select(subqueryRoot);

            Predicate userIdPredicate = criteriaBuilder.equal(path2.get("userId"), root.<String> get("login"));
            Predicate rolePredicate = criteriaBuilder.equal(subqueryRoot.get("roleId"), "op");

            subquery.select(subqueryRoot).where(userIdPredicate, rolePredicate);

            criteriaBuilder.exists(subquery);
            result = Specification.where(result)
                    .and(spec);
        }
        return result;
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






































