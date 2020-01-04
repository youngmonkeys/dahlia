package com.tvd12.dahlia.exception;

public class CollectionExistedException extends RuntimeException {
	private static final long serialVersionUID = 1422608286970669715L;

	public CollectionExistedException(String collectionName) {
		super("collection: " + collectionName + " existed");
	}
	
}
