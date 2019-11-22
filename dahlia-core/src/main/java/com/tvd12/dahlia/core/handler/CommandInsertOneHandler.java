package com.tvd12.dahlia.core.handler;

import com.tvd12.dahlia.core.command.InsertOne;
import com.tvd12.dahlia.core.constant.Constants;
import com.tvd12.dahlia.core.entity.Collection;
import com.tvd12.dahlia.core.entity.Record;
import com.tvd12.dahlia.core.setting.CollectionSetting;
import com.tvd12.dahlia.core.storage.CollectionStorage;
import com.tvd12.ezyfox.entity.EzyObject;

public class CommandInsertOneHandler extends CommandAbstractHandler<InsertOne> {

	@Override
	public Object handle(InsertOne command) {
		int collectionId = command.getCollectionId();
		EzyObject data = command.getData();
		Comparable id = data.get(Constants.FIELD_ID);
		
		Collection collection = databases.getCollection(collectionId);
		Record record = new Record(id, collection.getDataSize());
		collection.insert(record);
		
		CollectionSetting setting = collection.getSetting();
		CollectionStorage collectionStorage = storage.getCollectionStorage(collectionId);
		collectionStorage.storeRecord(record, setting.getFields(), data);
		return data;
	}
	
}
