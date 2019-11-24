package com.tvd12.dahlia.core.storage;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tvd12.dahlia.core.constant.Constants;
import com.tvd12.dahlia.core.io.Directories;
import com.tvd12.dahlia.core.setting.DatabaseSetting;
import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.codec.EzyObjectDeserializer;
import com.tvd12.ezyfox.codec.EzyObjectSerializer;

public class DatabaseStorage {

	protected final String databaseName;
	protected final String storageDirectory;
	protected final Path directoryPath;
	
	protected final EzyObjectSerializer objectSerializer;
	protected final EzyObjectDeserializer objectDeserializer;
	protected final DatabaseSettingStorage settingStorage;
	protected final Map<Integer, CollectionStorage> collectionStorages;
	
	protected DatabaseStorage(Builder builder) {
		this.databaseName = builder.databaseName;
		this.storageDirectory = builder.storageDirectory;
		this.directoryPath = getDirectoryPath();
		this.collectionStorages = new ConcurrentHashMap<>();
		this.objectSerializer = builder.objectSerializer;
		this.objectDeserializer = builder.objectDeserializer;
		this.mkdir();
		this.settingStorage = newSettingStorage();
	}
	
	protected Path getDirectoryPath() {
		return Paths.get(
				storageDirectory,
				Constants.DIRECTORY_DATABASES, databaseName);
	}
	
	protected DatabaseSettingStorage newSettingStorage() {
		return DatabaseSettingStorage.builder()
				.databaseDirectoryPath(directoryPath)
				.objectSerializer(objectSerializer)
				.objectDeserializer(objectDeserializer)
				.build();
	}
	
	protected void mkdir() {
		Path path = Paths.get(
				storageDirectory,
				Constants.DIRECTORY_DATABASES, databaseName);
		Directories.mkdirs(path.toFile());
	}
	
	public DatabaseSetting readSetting() {
		DatabaseSetting setting = settingStorage.read();
		setting.setDatabaseName(databaseName);
		return setting;
	}
	
	public void storeSetting(DatabaseSetting setting) {
		settingStorage.store(setting);
	}
	
	public CollectionStorage getCollectionStorage(int collectionId) {
		CollectionStorage storage = collectionStorages.get(collectionId);
		return storage;
	}
	
	public void addCollectionStorage(int collectionId, CollectionStorage storage) {
		this.collectionStorages.put(collectionId, storage);
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder implements EzyBuilder<DatabaseStorage> {
		
		protected String databaseName;
		protected String storageDirectory;
		protected EzyObjectSerializer objectSerializer;
		protected EzyObjectDeserializer objectDeserializer;
		
		public Builder databaseName(String databaseName) {
			this.databaseName = databaseName;
			return this;
		}
		
		public Builder storageDirectory(String storageDirectory) {
			this.storageDirectory = storageDirectory;
			return this;
		}
		
		public Builder objectSerializer(EzyObjectSerializer objectSerializer) {
			this.objectSerializer = objectSerializer;
			return this;
		}
		
		public Builder objectDeserializer(EzyObjectDeserializer objectDeserializer) {
			this.objectDeserializer = objectDeserializer;
			return this;
		}
		
		@Override
		public DatabaseStorage build() {
			return new DatabaseStorage(this);
		}
		
	}
	
}
