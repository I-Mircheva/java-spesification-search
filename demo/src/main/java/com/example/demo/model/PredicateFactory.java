package com.example.demo.model;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.*;
import java.util.*;

import java.util.stream.Collectors;

public class PredicateFactory {

    private static SpecSearchCriteria criteria;

    private static final Map<String, SearchOperation> operations = new HashMap<>();

    static {
        for (SearchOperation singleOperation : SearchOperation.values()) {
            operations.put(singleOperation.getMarker(), singleOperation);
        }
    }

    public static Specification<Customer> build(MultiValueMap<String, String> parameters){


        Specification<Customer> result = null;

        for(List<SpecSearchCriteria> one : grouping(parameters).values()) {
            result = Specification.where(result)
                    .and(groupSpec(one));
        }

        return result;
    }


    public static Specification<Customer> groupSpec(List<SpecSearchCriteria> groupedSpecs) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("kote");
        EntityManager em = emf.createEntityManager();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
        Root<Customer> customer = cq.from(Customer.class);

        String[] levels = groupedSpecs.get(0).getJoinPath().split("\\.");
        Path path = null;
        for(int i = 0; i < levels.length; i++) {
            if(path == null) {
                if(i == levels.length-1){
                    path = customer.get(levels[i]);
                } else {
                    path = customer.join(levels[i]);
                }
                continue;
            }
            if (i == levels.length - 1) {
                path = path.get(levels[i]);
            }else {
                path = ((From)path).join(levels[i]);
            }
        }


        Specification<Customer> spec = (Specification<Customer>) (root, query, criteriaBuilder) -> {
//                select c.*
//                from customer c inner join pet p on p.customer_id = c.id
//                where p.type = 'Cat'
//                and p.name = 'Joki'

            Join<Customer, Pet> ys = root.join("pets");
            return criteriaBuilder.and(criteriaBuilder.equal(ys.get("type"), "Bird"), criteriaBuilder.equal(ys.get("name"), "Joki"));
        };
        return spec;
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

            SpecSearchCriteria singleSpec = new SpecSearchCriteria(path, key, operations.get(pathKeyAndOperation[1]), entry.getValue());
            readySpecs.add(singleSpec);
        }

        Map<String, List<SpecSearchCriteria>> searchCriteriaListGroupedByPath = readySpecs.stream().collect(
                Collectors.groupingBy(SpecSearchCriteria::getJoinPath)
        );
        return searchCriteriaListGroupedByPath;
    }
}






































