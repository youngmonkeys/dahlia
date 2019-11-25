package com.tvd12.dahlia.core.handler;

import com.tvd12.dahlia.core.command.CreateCollection;
import com.tvd12.dahlia.core.entity.Collection;
import com.tvd12.dahlia.core.entity.Database;
import com.tvd12.dahlia.core.exception.CollectionExistedException;
import com.tvd12.dahlia.core.exception.DatabaseNotFoundException;
import com.tvd12.dahlia.core.factory.CollectionFactory;
import com.tvd12.dahlia.core.factory.CollectionFactoryAware;
import com.tvd12.dahlia.core.factory.CollectionStorageFactory;
import com.tvd12.dahlia.core.factory.CollectionStorageFactoryAware;
import com.tvd12.dahlia.core.setting.CollectionSetting;
import com.tvd12.dahlia.core.setting.RecordSizeReader;
import com.tvd12.dahlia.core.setting.RecordSizeReaderAware;
import com.tvd12.dahlia.core.setting.RuntimeSetting;
import com.tvd12.dahlia.core.setting.RuntimeSettingAware;
import com.tvd12.dahlia.core.storage.CollectionStorage;

import lombok.Setter;

public class CommandCreateCollectionHandler 
		extends CommandAbstractHandler<CreateCollection>
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
	public Object handle(CreateCollection command) {
		int databaseId = command.getDatabaseId();
		CollectionSetting setting = command.getSetting();
		String collectionName = setting.getCollectionName();
		Database database = databases.getDatabase(databaseId);
		if(database == null)
			throw new DatabaseNotFoundException(databaseId);
		Collection existedCollection = database.getCollection(collectionName);
		if(existedCollection != null)
			throw new CollectionExistedException(collectionName);
		int recordSize = recordSizeReader.read(setting.getFields());
		setting.setRecordSize(recordSize);
		Collection collection = collectionFactory.newCollection(setting);
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
