package com.tvd12.dahlia.core;

import com.tvd12.dahlia.core.entity.Collection;
import com.tvd12.dahlia.core.entity.Database;
import com.tvd12.dahlia.core.entity.Databases;
import com.tvd12.dahlia.core.entity.Record;
import com.tvd12.dahlia.core.factory.CollectionFactory;
import com.tvd12.dahlia.core.factory.CollectionStorageFactory;
import com.tvd12.dahlia.core.factory.DatabaseFactory;
import com.tvd12.dahlia.core.factory.DatabaseStorageFactory;
import com.tvd12.dahlia.core.setting.CollectionSetting;
import com.tvd12.dahlia.core.setting.DatabaseSetting;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.dahlia.core.setting.RuntimeSetting;
import com.tvd12.dahlia.core.storage.CollectionStorage;
import com.tvd12.dahlia.core.storage.DatabaseStorage;
import com.tvd12.dahlia.core.storage.Storage;
import com.tvd12.ezyfox.codec.EzyObjectDeserializer;
import com.tvd12.ezyfox.codec.EzyObjectSerializer;
import com.tvd12.ezyfox.codec.MsgPackSimpleDeserializer;
import com.tvd12.ezyfox.codec.MsgPackSimpleSerializer;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;

import static com.tvd12.dahlia.core.constant.Constants.DIRECTORY_DATABASES;

public class DahliaCoreLoader {

    protected String storageDirectory = "data";
    protected EzyObjectSerializer objectSerializer;
    protected EzyObjectDeserializer objectDeserializer;
    protected DatabaseStorageFactory databaseStorageFactory;
    protected CollectionStorageFactory collectionStorageFactory;

    public DahliaCoreLoader() {
        this.objectSerializer = new MsgPackSimpleSerializer();
        this.objectDeserializer = new MsgPackSimpleDeserializer();
    }

    public DahliaCoreLoader storageDirectory(String storageDirectory) {
        this.storageDirectory = storageDirectory;
        return this;
    }

    public DahliaCore load() {
        this.initComponents();
        return doLoad();
    }

    protected void initComponents() {
        this.databaseStorageFactory = newDatabaseStorageFactory();
        this.collectionStorageFactory = newCollectionStorageFactory();
    }

    protected DahliaCore doLoad() {
        Map<String, DatabaseStorage> databaseStorages = newDatabaseStorages();
        Set<String> databaseNames = databaseStorages.keySet();
        Map<String, List<CollectionStorage>>
            collectionStoragesMap = newDatabaseCollectionsStorages(databaseNames);
        Map<String, DatabaseSetting> databaseSettings = readDatabaseSettings(databaseStorages);
        Map<String, List<CollectionSetting>> collectionSettingsMap = readCollectionSettings(collectionStoragesMap);

        Storage storage = newStorage(databaseStorages, collectionStoragesMap, databaseSettings, collectionSettingsMap);
        RuntimeSetting runtimeSetting = storage.readRuntimeSetting();
        DatabaseFactory databaseFactory = new DatabaseFactory(runtimeSetting.getMaxDatabaseId());
        CollectionFactory collectionFactory = new CollectionFactory(runtimeSetting.getMaxCollectionId());
        Databases databases = newDatabases(databaseFactory, collectionFactory, databaseSettings, collectionSettingsMap);

        loadAllCollections(databases, storage);

        return DahliaCore.builder()
            .storage(storage)
            .databases(databases)
            .runtimeSetting(runtimeSetting)
            .databaseFactory(databaseFactory)
            .collectionFactory(collectionFactory)
            .databaseStorageFactory(databaseStorageFactory)
            .collectionStorageFactory(collectionStorageFactory)
            .build();
    }

    protected DatabaseStorageFactory newDatabaseStorageFactory() {
        return DatabaseStorageFactory.builder()
            .storageDirectory(storageDirectory)
            .objectSerializer(objectSerializer)
            .objectDeserializer(objectDeserializer)
            .build();
    }

    protected CollectionStorageFactory newCollectionStorageFactory() {
        return CollectionStorageFactory.builder()
            .storageDirectory(storageDirectory)
            .objectSerializer(objectSerializer)
            .objectDeserializer(objectDeserializer)
            .build();
    }

    protected Databases newDatabases(
        DatabaseFactory databaseFactory,
        CollectionFactory collectionFactory,
        Map<String, DatabaseSetting> databaseSettings,
        Map<String, List<CollectionSetting>> collectionSettingsMap) {
        Databases databases = new Databases();
        for (String databaseName : databaseSettings.keySet()) {
            DatabaseSetting databaseSetting = databaseSettings.get(databaseName);
            Database database = databaseFactory.createDatabase(databaseSetting);
            List<CollectionSetting> collectionSettings = collectionSettingsMap.get(databaseName);
            for (CollectionSetting collectionSetting : collectionSettings) {
                Collection collection = collectionFactory.createCollection(collectionSetting);
                database.addCollection(collection);
                databases.addCollection(collection);
            }
            databases.addDatabase(database);
        }
        return databases;
    }

    protected Storage newStorage(
        Map<String, DatabaseStorage> databaseStorages,
        Map<String, List<CollectionStorage>> collectionStoragesMap,
        Map<String, DatabaseSetting> databaseSettings,
        Map<String, List<CollectionSetting>> collectionSettingsMap) {
        Storage storage = Storage.builder()
            .directory(storageDirectory)
            .objectSerializer(objectSerializer)
            .objectDeserializer(objectDeserializer)
            .build();
        for (String databaseName : databaseStorages.keySet()) {
            DatabaseStorage databaseStorage = databaseStorages.get(databaseName);
            DatabaseSetting databaseSetting = databaseSettings.get(databaseName);
            storage.addDatabaseStorage(databaseSetting.getDatabaseId(), databaseStorage);
        }
        for (String databaseName : databaseStorages.keySet()) {
            DatabaseStorage databaseStorage = databaseStorages.get(databaseName);
            List<CollectionSetting> collectionSettings = collectionSettingsMap.get(databaseName);
            List<CollectionStorage> collectionStorages = collectionStoragesMap.get(databaseName);
            for (int i = 0; i < collectionSettings.size(); ++i) {
                CollectionSetting collectionSetting = collectionSettings.get(i);
                CollectionStorage collectionStorage = collectionStorages.get(i);
                databaseStorage.addCollectionStorage(collectionSetting.getCollectionId(), collectionStorage);
                storage.addCollectionStorage(collectionSetting.getCollectionId(), collectionStorage);
            }
        }
        return storage;
    }

    protected Map<String, DatabaseStorage> newDatabaseStorages() {
        Map<String, DatabaseStorage> databaseStorages = new HashMap<>();
        File databasesDirectory = Paths.get(storageDirectory, DIRECTORY_DATABASES).toFile();
        if (!databasesDirectory.exists()) {
            //noinspection ResultOfMethodCallIgnored
            databasesDirectory.mkdirs();
        }
        for (File databaseDirectory : Objects.requireNonNull(databasesDirectory.listFiles())) {
            if (databaseDirectory.isFile()) {
                continue;
            }
            String databaseName = databaseDirectory.getName();
            DatabaseStorage databaseStorage = newDatabaseStorage(databaseName);
            databaseStorages.put(databaseName, databaseStorage);
        }
        return databaseStorages;
    }

    protected Map<String, List<CollectionStorage>> newDatabaseCollectionsStorages(
        Set<String> databaseNames
    ) {
        Map<String, List<CollectionStorage>> collectionStorages = new HashMap<>();
        for (String databaseName : databaseNames) {
            collectionStorages.put(databaseName, newCollectionsStorages(databaseName));
        }
        return collectionStorages;
    }

    protected List<CollectionStorage> newCollectionsStorages(String databaseName) {
        List<CollectionStorage> collectionStorages = new ArrayList<>();
        File databaseDirectory = Paths.get(
            storageDirectory, DIRECTORY_DATABASES, databaseName).toFile();
        //noinspection ConstantConditions
        for (File collectionDirectory : databaseDirectory.listFiles()) {
            if (collectionDirectory.isFile()) {
                continue;
            }
            String collectionName = collectionDirectory.getName();
            CollectionStorage storage = newCollectionStorage(databaseName, collectionName);
            collectionStorages.add(storage);
        }
        return collectionStorages;
    }

    protected DatabaseStorage newDatabaseStorage(String databaseName) {
        return databaseStorageFactory
            .newDatabaseStorage(databaseName);
    }

    protected CollectionStorage newCollectionStorage(
        String databaseName,
        String collectionName
    ) {
        return collectionStorageFactory
            .newCollectionStorage(collectionName, databaseName);
    }

    protected Map<String, DatabaseSetting> readDatabaseSettings(
        Map<String, DatabaseStorage> storages
    ) {
        Map<String, DatabaseSetting> settings = new HashMap<>();
        for (String databaseName : storages.keySet()) {
            DatabaseStorage storage = storages.get(databaseName);
            DatabaseSetting setting = storage.readSetting();
            settings.put(databaseName, setting);
        }
        return settings;
    }

    protected Map<String, List<CollectionSetting>> readCollectionSettings(
        Map<String, List<CollectionStorage>> storages
    ) {
        Map<String, List<CollectionSetting>> settings = new HashMap<>();
        for (String databaseName : storages.keySet()) {
            List<CollectionSetting> collectionSettings = new ArrayList<>();
            List<CollectionStorage> collectionStorages = storages.get(databaseName);
            for (CollectionStorage storage : collectionStorages) {
                CollectionSetting setting = storage.readSetting();
                collectionSettings.add(setting);
            }
            settings.put(databaseName, collectionSettings);
        }
        return settings;
    }

    protected void loadAllCollections(Databases databases, Storage storage) {
        for (Database database : databases.getDatabaseList()) {
            DatabaseStorage databaseStorage = storage.getDatabaseStore(database.getId());
            loadCollections(database, databaseStorage);
        }
    }

    protected void loadCollections(Database database, DatabaseStorage storage) {
        for (Collection collection : database.getCollectionList()) {
            CollectionStorage collectionStorage = storage.getCollectionStorage(collection.getId());
            loadCollection(collection, collectionStorage);
        }
    }

    protected void loadCollection(Collection collection, CollectionStorage storage) {
        CollectionSetting setting = collection.getSetting();
        FieldSetting idSetting = setting.getId();
        long recordPosition = collection.getDataSize();
        while (storage.hasMoreRecords(recordPosition)) {
            Record record = storage.readRecord(recordPosition, idSetting);
            if (record != null) {
                collection.insert(record);
            }
            recordPosition = collection.increaseDataSize();
        }
    }

}
