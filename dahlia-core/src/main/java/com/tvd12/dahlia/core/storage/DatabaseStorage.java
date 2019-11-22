package com.tvd12.dahlia.core.storage;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tvd12.dahlia.core.constant.Constants;
import com.tvd12.dahlia.core.io.Directories;
import com.tvd12.dahlia.core.setting.CollectionSetting;

public class DatabaseStorage {

	protected final String databaseName;
	protected final String storageDirectory;
	protected final Map<Integer, CollectionStorage> collectionStorages;
	
	public DatabaseStorage(String databaseName, String storageDirectory) {
		this.databaseName = databaseName;
		this.storageDirectory = storageDirectory;
		this.collectionStorages = new ConcurrentHashMap<>();
	}
	
	public void mkdir() {
		Path path = Paths.get(
				storageDirectory,
				Constants.DIRECTORY_DATABASES, databaseName);
		Directories.mkdirs(path.toFile());
	}
	
	public CollectionStorage createCollectionStorage(CollectionSetting setting) {
		int collectionId = setting.getCollectionId();
		String collectionName = setting.getCollectionName();
		CollectionStorage storage = new CollectionStorage(collectionName, databaseName, storageDirectory);
		collectionStorages.put(collectionId, storage);
		return storage;
	}
	
}
