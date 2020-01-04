package com.tvd12.dahlia.exception;

public class CollectionNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1422608286970669715L;

	public CollectionNotFoundException(int collectionId) {
		super("collection with id: " + collectionId + " not found");
	}
	
}
