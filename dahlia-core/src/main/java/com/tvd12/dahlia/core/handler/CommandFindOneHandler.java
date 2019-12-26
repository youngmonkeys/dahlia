package com.tvd12.dahlia.core.handler;

import com.tvd12.dahlia.core.command.FindOne;
import com.tvd12.dahlia.core.entity.Collection;
import com.tvd12.dahlia.core.entity.Record;
import com.tvd12.dahlia.core.exception.CollectionNotFoundException;
import com.tvd12.dahlia.core.setting.CollectionSetting;
import com.tvd12.dahlia.core.storage.CollectionStorage;

public class CommandFindOneHandler extends CommandAbstractHandler<FindOne> {

	@Override
	public Object handle(FindOne command) {
		int collectionId = command.getCollectionId();
		Comparable id = command.getId();
		
		Collection collection = databases.getCollection(collectionId);
		if(collection == null)
			throw new CollectionNotFoundException(collectionId);
		Record record = collection.findById(id);
		
		CollectionSetting setting = collection.getSetting();
		CollectionStorage collectionStorage = storage.getCollectionStorage(collectionId);
		Object value = collectionStorage.readRecord(record, setting.getId(), setting.getFields());
		return value;
	}
	
}
