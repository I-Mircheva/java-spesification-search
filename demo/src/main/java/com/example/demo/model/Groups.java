package com.example.demo.model;

import java.util.*;
import java.util.stream.Collectors;

public class Groups {

    private static final Map<String, SearchOperation> operations = new HashMap<>();

    static {
        for (SearchOperation singleOperation : SearchOperation.values()) {
            operations.put(singleOperation.getMarker(), singleOperation);
        }
    }

    public static Map<String, List<SpecificationSearchCriteria>> grouping(Map<String, String> parameters){
        List<SpecificationSearchCriteria> readySpecs = new ArrayList<>();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String key1 = entry.getKey();
            if(key1.equals("page") || key1.equals("sort") || key1.equals("size")) {
                continue;
            }

            // [ pets.name-EQ ] becomes (hopefully) [ pets.name ] [ EQ ]
            String[] pathKeyAndOperation = entry.getKey().split("-");

            // [ pets.name ] becomes (hopefully) [ pets ] [ name ]
            List<String> list = new ArrayList<>(Arrays.asList(pathKeyAndOperation[0].split("\\.")));

            int indexOfKey = list.size() - 1;
            // key is [ name ]
            String key = list.remove(indexOfKey);

            // Join the path to use later with "." delimiter
            String path = String.join(".", list);

            SpecificationSearchCriteria singleSpec;

            if(pathKeyAndOperation.length < 2) {
                singleSpec = new SpecificationSearchCriteria(path, key, SearchOperation.EQUALITY, entry.getValue());
            } else {
                singleSpec = new SpecificationSearchCriteria(path, key, operations.get(pathKeyAndOperation[1]), entry.getValue());
            }


            readySpecs.add(singleSpec);
        }

        return readySpecs.stream().collect(
                Collectors.groupingBy(SpecificationSearchCriteria::getJoinPath)
        );
    }
}
