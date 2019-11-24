package com.tvd12.dahlia.core.storage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tvd12.dahlia.core.setting.RuntimeSetting;
import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.codec.EzyObjectDeserializer;
import com.tvd12.ezyfox.codec.EzyObjectSerializer;

public class Storage {

	protected final String directory;
	protected final EzyObjectSerializer objectSerializer;
	protected final EzyObjectDeserializer objectDeserializer;
	protected final RuntimeSettingStorage runtimeSettingStorage;
	protected final Map<Integer, DatabaseStorage> databaseStorages;
	protected final Map<Integer, CollectionStorage> collectionStorages;
	
	protected Storage(Builder builder) {
		this.directory = builder.directory;
		this.objectSerializer = builder.objectSerializer;
		this.objectDeserializer = builder.objectDeserializer;
		this.databaseStorages = new ConcurrentHashMap<>();
		this.collectionStorages = new ConcurrentHashMap<>();
		this.runtimeSettingStorage = newRuntimeSettingStorage();
	}
	
	protected RuntimeSettingStorage newRuntimeSettingStorage() {
		return RuntimeSettingStorage.builder()
				.storageDirectory(directory)
				.objectSerializer(objectSerializer)
				.objectDeserializer(objectDeserializer)
				.build();
	}
	
	public RuntimeSetting readRuntimeSetting() {
		RuntimeSetting setting = runtimeSettingStorage.read();
		return setting;
	}
	
	public void storeRuntimeSetting(RuntimeSetting setting) {
		runtimeSettingStorage.store(setting);
	}
	
	public DatabaseStorage getDatabaseStore(int databaseId) {
		DatabaseStorage storage = databaseStorages.get(databaseId);
		return storage;
	}
	
	public void addDatabaseStorage(int databaseId, DatabaseStorage storage) {
		this.databaseStorages.put(databaseId, storage);
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
	
	public static class Builder implements EzyBuilder<Storage> {
		
		protected String directory;
		protected String storageDirectory;
		protected EzyObjectSerializer objectSerializer;
		protected EzyObjectDeserializer objectDeserializer;
		
		public Builder directory(String directory) {
			this.directory = directory;
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
		public Storage build() {
			return new Storage(this);
		}
		
	}
	
}
