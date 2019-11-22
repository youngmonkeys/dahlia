package com.tvd12.dahlia.core.storage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tvd12.dahlia.core.setting.DatabaseSetting;

public class Storage {

	protected final String directory;
	protected final Map<Integer, DatabaseStorage> databaseStorages;
	protected final Map<Integer, CollectionStorage> collectionStorages;
	
	public Storage(String directory) {
		this.directory = directory;
		this.databaseStorages = new ConcurrentHashMap<>();
		this.collectionStorages = new ConcurrentHashMap<>();
	}
	
	public DatabaseStorage createDatabaseStorage(DatabaseSetting setting) {
		int databaseId = setting.getDatabaseId();
		String databaseName = setting.getDatabaseName();
		DatabaseStorage databaseStorage = new DatabaseStorage(databaseName, directory);
		databaseStorages.put(databaseId, databaseStorage);
		return databaseStorage;
	}
	
	public CollectionStorage getCollectionStorage(int collectionId) {
		CollectionStorage storage = collectionStorages.get(collectionId);
		return storage;
	}
	
	public void addCollectionStorage(int collectionId, CollectionStorage storage) {
		this.collectionStorages.put(collectionId, storage);
	}
	
}
