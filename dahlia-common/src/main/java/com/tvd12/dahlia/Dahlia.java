package com.tvd12.dahlia;

public interface Dahlia {

	IDatabase getDatabase(String name);
	
	IDatabase createDatabase(Object setting);
	
}
