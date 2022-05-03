package com.tvd12.dahlia.core.handler;

import com.tvd12.dahlia.core.command.CommandCreateCollection;
import com.tvd12.dahlia.core.entity.Collection;
import com.tvd12.dahlia.core.entity.Database;
import com.tvd12.dahlia.core.factory.CollectionFactory;
import com.tvd12.dahlia.core.factory.CollectionFactoryAware;
import com.tvd12.dahlia.core.factory.CollectionStorageFactory;
import com.tvd12.dahlia.core.factory.CollectionStorageFactoryAware;
import com.tvd12.dahlia.core.setting.*;
import com.tvd12.dahlia.core.storage.CollectionStorage;
import com.tvd12.dahlia.exception.CollectionExistedException;
import com.tvd12.dahlia.exception.DatabaseNotFoundException;
import lombok.Setter;

public class CommandCreateCollectionHandler
    extends CommandAbstractHandler<CommandCreateCollection>
    implements
    RuntimeSettingAware,
    RecordSizeReaderAware,
    CollectionFactoryAware,
    CollectionStorageFactoryAware {

    @Setter
    protected RuntimeSetting runtimeSetting;
    @Setter
    protected RecordSizeReader recordSizeReader;
    @Setter
    protected CollectionFactory collectionFactory;
    @Setter
    protected CollectionStorageFactory collectionStorageFactory;

    @Override
    public Object handle(CommandCreateCollection command) {
        int databaseId = command.getDatabaseId();
        CollectionSetting setting = command.getSetting();
        String collectionName = setting.getCollectionName();
        Database database = databases.getDatabase(databaseId);
        if (database == null) {
            throw new DatabaseNotFoundException(databaseId);
        }
        Collection existedCollection = database.getCollection(collectionName);
        if (existedCollection != null) {
            throw new CollectionExistedException(collectionName);
        }
        int recordSize = recordSizeReader.read(setting.getAllFields());
        setting.setRecordSize(recordSize);
        Collection collection = collectionFactory.newCollection(setting);
        //noinspection SynchronizeOnNonFinalField
        synchronized (runtimeSetting) {
            runtimeSetting.setMaxCollectionId(collection.getId());
            storage.storeRuntimeSetting(runtimeSetting);
        }
        database.addCollection(collection);
        databases.addCollection(collection);
        CollectionStorage collectionStorage =
            collectionStorageFactory.newCollectionStorage(collectionName, database.getName());
        storage.addCollectionStorage(collection.getId(), collectionStorage);
        collectionStorage.storeSetting(setting);
        return collection;
    }

}
