package com.example.demo.model;

public class SpecSearchCriteria {

    private String joinPath;
    private String key;
    private SearchOperation operation;
    private Object value;

    public SpecSearchCriteria() {

    }

    public SpecSearchCriteria(final String joinPath, final String key, final SearchOperation operation, final Object value) {
        super();
        this.joinPath = joinPath;
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    public String getJoinPath() {
        return joinPath;
    }

    public void setJoinPath(String joinPath) {
        this.joinPath = joinPath;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public SearchOperation getOperation() {
        return operation;
    }

    public void setOperation(final SearchOperation operation) {
        this.operation = operation;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(final Object value) {
        this.value = value;
    }

}
