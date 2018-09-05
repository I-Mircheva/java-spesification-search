package com.example.demo.factory;

import com.example.demo.model.*;
import org.springframework.data.jpa.domain.Specification;
import java.util.*;


public class PredicateFactory {


    private static final Map<String, SearchOperation> operations = new HashMap<>();

    static {
        for (SearchOperation singleOperation : SearchOperation.values()) {
            operations.put(singleOperation.getMarker(), singleOperation);
        }
    }

    public static Specification<Customer> build(Map<String, String> parameters){

        if(parameters.isEmpty())return null;

        Collection<List<SpecificationSearchCriteria>> groups = Groups.grouping(parameters).values();
        if(groups.isEmpty())return null;

        Iterator<List<SpecificationSearchCriteria>> it = groups.iterator();

        SpecificationsFactory<Customer> specifications = new SpecificationsFactory<>();

        Specification<Customer> result = specifications.groupSpec(it.next());
        while (it.hasNext()) {
            result = Specification.where(result)
                    .and(specifications.groupSpec(it.next()));
        }

        return result;
    }

}






































