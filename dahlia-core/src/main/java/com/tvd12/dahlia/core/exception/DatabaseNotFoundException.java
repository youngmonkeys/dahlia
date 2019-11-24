package com.tvd12.dahlia.core.exception;

public class DatabaseNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1422608286970669715L;

	public DatabaseNotFoundException(int databaseId) {
		super("database with id: " + databaseId + " existed");
	}
	
}
