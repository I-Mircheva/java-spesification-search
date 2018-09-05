package com.example.demo.model;

public class SpecificationSearchCriteria {

    private String joinPath;
    private String key;
    private SearchOperation operation;
    private Object value;

    public SpecificationSearchCriteria() {}

    public SpecificationSearchCriteria(final String joinPath, final String key, final SearchOperation operation, final Object value) {
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
        if(value != null) {
            if ("true".equals(value.toString())) {
                return Boolean.TRUE;
            }
            if("false".equals(value.toString())) {
                return Boolean.FALSE;
            }
        }
        return value;
    }

    public void setValue(final Object value) {
        this.value = value;
    }

}
