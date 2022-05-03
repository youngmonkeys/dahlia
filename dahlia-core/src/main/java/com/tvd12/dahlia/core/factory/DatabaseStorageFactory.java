package com.tvd12.dahlia.core.factory;

import com.tvd12.dahlia.core.storage.DatabaseStorage;
import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.codec.EzyObjectDeserializer;
import com.tvd12.ezyfox.codec.EzyObjectSerializer;

public class DatabaseStorageFactory {

    protected final String storageDirectory;
    protected final EzyObjectSerializer objectSerializer;
    protected final EzyObjectDeserializer objectDeserializer;

    protected DatabaseStorageFactory(Builder builder) {
        this.storageDirectory = builder.storageDirectory;
        this.objectSerializer = builder.objectSerializer;
        this.objectDeserializer = builder.objectDeserializer;
    }

    public static Builder builder() {
        return new Builder();
    }

    public DatabaseStorage newDatabaseStorage(String databaseName) {
        return DatabaseStorage.builder()
            .databaseName(databaseName)
            .storageDirectory(storageDirectory)
            .objectSerializer(objectSerializer)
            .objectDeserializer(objectDeserializer)
            .build();
    }

    public static class Builder implements EzyBuilder<DatabaseStorageFactory> {

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
        public DatabaseStorageFactory build() {
            return new DatabaseStorageFactory(this);
        }
    }
}
