package com.tvd12.dahlia.core.handler;

import com.tvd12.dahlia.core.command.InsertOne;
import com.tvd12.dahlia.core.constant.Constants;
import com.tvd12.dahlia.core.entity.Collection;
import com.tvd12.dahlia.core.entity.Databases;
import com.tvd12.dahlia.core.entity.Record;
import com.tvd12.dahlia.core.setting.CollectionSetting;
import com.tvd12.dahlia.core.storage.CollectionStorage;
import com.tvd12.dahlia.core.storage.Storage;
import com.tvd12.ezyfox.entity.EzyObject;

public class CommandInsertOneHandler extends CommandAbstractHandler<InsertOne> {

	protected Databases databases;
	protected Storage storage;
	
	@Override
	public Object handle(InsertOne command) {
		String collectionName = command.getCollectionName();
		EzyObject data = command.getData();
		Comparable id = data.get(Constants.FIELD_ID);
		
		Collection collection = databases.getCollection(collectionName);
		Record record = new Record(id, collection.getDataSize());
		collection.insert(record);
		
		CollectionSetting setting = collection.getSetting();
		CollectionStorage collectionStorage = storage.getCollectionStorage(collectionName);
		collectionStorage.storeRecord(record, setting.getFields(), data);
		return data;
	}
	
}
