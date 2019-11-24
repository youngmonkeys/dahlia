package com.tvd12.dahlia.core.factory;

import com.tvd12.dahlia.core.storage.CollectionStorage;
import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.codec.EzyObjectDeserializer;
import com.tvd12.ezyfox.codec.EzyObjectSerializer;

public class CollectionStorageFactory {

	protected final String storageDirectory;
	protected final EzyObjectSerializer objectSerializer;
	protected final EzyObjectDeserializer objectDeserializer;
	
	protected CollectionStorageFactory(Builder builder) {
		this.storageDirectory = builder.storageDirectory;
		this.objectSerializer = builder.objectSerializer;
		this.objectDeserializer = builder.objectDeserializer;
	}
	
	public CollectionStorage newCollectionStorage(
			String collectionName, String databaseName) {
		return CollectionStorage.builder()
				.databaseName(databaseName)
				.collectionName(collectionName)
				.storageDirectory(storageDirectory)
				.objectSerializer(objectSerializer)
				.objectDeserializer(objectDeserializer)
				.build();
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder implements EzyBuilder<CollectionStorageFactory> {
		
		protected String storageDirectory;
		protected EzyObjectSerializer objectSerializer;
		protected EzyObjectDeserializer objectDeserializer;
		
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
		public CollectionStorageFactory build() {
			return new CollectionStorageFactory(this);
		}
		
	}
	
}
