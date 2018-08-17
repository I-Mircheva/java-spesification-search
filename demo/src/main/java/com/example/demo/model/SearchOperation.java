package com.example.demo.model;

public enum SearchOperation {
    EQUALITY("EQ"), NEGATION("NE"), GREATER_THAN("GT"), LESS_THAN("LT"), LIKE("LIKE"), STARTS_WITH("SW"), ENDS_WITH("EW"), CONTAINS("CON"), GREATER_OR_EQUALS("GOE");

    private final String marker;

    SearchOperation(String marker) {
        this.marker = marker;
    }

    public String getMarker() {
        return marker;
    }
}
