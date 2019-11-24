package com.tvd12.dahlia.core.exception;

public class DatabaseExistedException extends RuntimeException {
	private static final long serialVersionUID = 1422608286970669715L;

	public DatabaseExistedException(String databaseName) {
		super("database: " + databaseName + " existed");
	}
	
}
