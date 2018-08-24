package com.example.demo.model;

public enum SearchOperation {
    EQUALITY("EQ"), GREATER_THAN("GT"), LESS_THAN("LT"), GREATER_OR_EQUALS("GOE"), LESS_OR_EQUALS("LOE");

    private final String marker;

    SearchOperation(String marker) {
        this.marker = marker;
    }

    public String getMarker() {
        return marker;
    }
}
