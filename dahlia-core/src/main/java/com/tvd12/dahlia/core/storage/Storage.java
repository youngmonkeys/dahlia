package com.tvd12.dahlia.core.storage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Storage {

	protected final String directory;
	protected final Map<String, DatabaseStorage> databaseStorages;
	
	public Storage(String directory) {
		this.directory = directory;
		this.databaseStorages = new ConcurrentHashMap<>();
	}
	
	public DatabaseStorage createDatabaseStorage(String databaseName) {
		DatabaseStorage databaseStorage = new DatabaseStorage(databaseName, directory);
		databaseStorages.put(databaseName, databaseStorage);
		return databaseStorage;
	}
	
}
