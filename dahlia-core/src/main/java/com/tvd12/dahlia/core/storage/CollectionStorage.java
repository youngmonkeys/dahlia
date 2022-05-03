package com.tvd12.dahlia.core.storage;

import com.tvd12.dahlia.core.constant.Constants;
import com.tvd12.dahlia.core.entity.Record;
import com.tvd12.dahlia.core.io.Directories;
import com.tvd12.dahlia.core.setting.CollectionSetting;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.codec.EzyObjectDeserializer;
import com.tvd12.ezyfox.codec.EzyObjectSerializer;
import com.tvd12.ezyfox.entity.EzyObject;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class CollectionStorage {

    protected final String databaseName;
    protected final String collectionName;
    protected final String storageDirectory;
    protected final Path directoryPath;

    protected final CollectionRecordStore recordStore;
    protected final CollectionSettingStorage settingStorage;
    protected final EzyObjectSerializer objectSerializer;
    protected final EzyObjectDeserializer objectDeserializer;

    protected CollectionStorage(Builder builder) {
        this.databaseName = builder.databaseName;
        this.collectionName = builder.collectionName;
        this.storageDirectory = builder.storageDirectory;
        this.directoryPath = getDirectoryPath();
        this.objectSerializer = builder.objectSerializer;
        this.objectDeserializer = builder.objectDeserializer;
        this.mkdir();
        this.recordStore = newRecordStorage();
        this.settingStorage = newSettingStorage();
    }

    public static Builder builder() {
        return new Builder();
    }

    protected Path getDirectoryPath() {
        return Paths.get(
            storageDirectory,
            Constants.DIRECTORY_DATABASES, databaseName, collectionName);
    }

    protected CollectionRecordStore newRecordStorage() {
        return new CollectionRecordStore(directoryPath);
    }

    protected CollectionSettingStorage newSettingStorage() {
        return CollectionSettingStorage.builder()
            .collectionDirectoryPath(directoryPath)
            .objectSerializer(objectSerializer)
            .objectDeserializer(objectDeserializer)
            .build();
    }

    protected void mkdir() {
        Directories.mkdirs(directoryPath.toFile());
    }

    public void storeSetting(CollectionSetting setting) {
        settingStorage.store(setting);
    }

    public CollectionSetting readSetting() {
        CollectionSetting setting = settingStorage.read();
        setting.setCollectionName(collectionName);
        return setting;
    }

    public boolean hasMoreRecords(long position) {
        return recordStore.hasMoreRecords(position);
    }

    public Record readRecord(
        long position,
        FieldSetting idSetting) {
        return recordStore.read(position, idSetting);
    }

    public EzyObject readRecord(
        Record record,
        FieldSetting idSetting,
        Map<String, FieldSetting> settings) {
        return recordStore.read(record, idSetting, settings);
    }

    public void storeRecord(
        Record record,
        FieldSetting idSetting,
        Map<String, FieldSetting> settings, EzyObject data) {
        recordStore.write(record, idSetting, settings, data);
    }

    public static class Builder implements EzyBuilder<CollectionStorage> {

        protected String databaseName;
        protected String collectionName;
        protected String storageDirectory;
        protected EzyObjectSerializer objectSerializer;
        protected EzyObjectDeserializer objectDeserializer;

        public Builder databaseName(String databaseName) {
            this.databaseName = databaseName;
            return this;
        }

        public Builder collectionName(String collectionName) {
            this.collectionName = collectionName;
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
        public CollectionStorage build() {
            return new CollectionStorage(this);
        }
    }
}
