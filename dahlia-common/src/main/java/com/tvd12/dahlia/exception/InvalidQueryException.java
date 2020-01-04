package com.tvd12.dahlia.exception;

public class InvalidQueryException extends IllegalArgumentException {
	private static final long serialVersionUID = -4700562332465108722L;

	public InvalidQueryException(String msg) {
		super(msg);
	}
	
	public InvalidQueryException(String msg, Exception e) {
		super(msg, e);
	}
	
}
