package com.tvd12.dahlia.core.storage;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.tvd12.dahlia.core.constant.Constants;
import com.tvd12.dahlia.core.io.Directories;

public class DatabaseStorage {

	protected String databaseName;
	protected String storageDirectory;
	
	public DatabaseStorage(String databaseName, String storageDirectory) {
		this.databaseName = databaseName;
		this.storageDirectory = storageDirectory;
	}
	
	public void mkdir() {
		Path path = Paths.get(
				storageDirectory,
				Constants.DIRECTORY_DATABASES, databaseName);
		Directories.mkdirs(path.toFile());
	}
	
	public CollectionStorage createCollectionStorage(String collectionName) {
		return new CollectionStorage(collectionName, databaseName, storageDirectory);
	}
	
}
