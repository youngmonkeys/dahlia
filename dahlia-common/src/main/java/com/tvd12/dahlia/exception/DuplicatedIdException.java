package com.tvd12.dahlia.exception;

@SuppressWarnings("rawtypes")
public class DuplicatedIdException extends RuntimeException {
    private static final long serialVersionUID = 1422608286970669715L;

    public DuplicatedIdException(String collectionName, Comparable id) {
        super("duplicate id: " + id + " on collection: " + collectionName);
    }
}
