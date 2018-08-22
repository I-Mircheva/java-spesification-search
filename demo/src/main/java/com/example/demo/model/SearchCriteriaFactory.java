package com.example.demo.model;

import org.springframework.util.MultiValueMap;

import java.util.*;

public class SearchCriteriaFactory {

    private static final Map<String, SearchOperation> operations = new HashMap<>();

    static {
        for (SearchOperation singleOperation : SearchOperation.values()) {
            operations.put(singleOperation.getMarker(), singleOperation);
        }
    }

    public static SpecSearchCriteria build(String key, List<String> value) {
        Map.Entry<String,SearchOperation> keyAndMarker = keyAndMarkerGeneration(key);
        if(keyAndMarker.getValue() != null) {
//            return new SpecSearchCriteria(keyAndMarker.getKey(), keyAndMarker.getValue(), value.get(0)); //removed Integer.valueOf(value.get(0))
        }
        return null;
    }

    private static Map.Entry<String,SearchOperation> keyAndMarkerGeneration(String key) {
        String[] pathAndMarker = key.split("-");
//        String[] pathAndKey =pathAndMarker[0].split(".");
        if(pathAndMarker.length < 2) {
            return new AbstractMap.SimpleEntry<>(pathAndMarker[0], SearchOperation.EQUALITY);
        }
        return new AbstractMap.SimpleEntry<>(pathAndMarker[0], operations.get(pathAndMarker[1]));
    }


    public static List<SpecSearchCriteria> build(MultiValueMap<String, String> parameters){

        List<SpecSearchCriteria> readySpecs = new ArrayList<>();

        for (Map.Entry<String, List<String>> entry : parameters.entrySet()) {

            SpecSearchCriteria singleSpec = SearchCriteriaFactory.build(entry.getKey(), entry.getValue());
            readySpecs.add(singleSpec);
        }
        return readySpecs;
    }
}
