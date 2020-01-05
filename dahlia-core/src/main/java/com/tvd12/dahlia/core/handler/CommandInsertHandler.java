package com.tvd12.dahlia.core.handler;

import java.util.Map;
import java.util.UUID;

import com.tvd12.dahlia.core.command.CommandInsert;
import com.tvd12.dahlia.core.constant.Constants;
import com.tvd12.dahlia.core.entity.Collection;
import com.tvd12.dahlia.core.entity.Record;
import com.tvd12.dahlia.core.setting.CollectionSetting;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.dahlia.core.storage.CollectionStorage;
import com.tvd12.dahlia.exception.DuplicatedIdException;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;

public class CommandInsertHandler extends CommandAbstractHandler<CommandInsert> {

	@Override
	public Object handle(CommandInsert command) {
		int collectionId = command.getCollectionId();
		EzyArray data = command.getData();
		
		Collection collection = databases.getCollection(collectionId);
		CollectionSetting setting = collection.getSetting();
		FieldSetting sId = setting.getId();
		Map<String, FieldSetting> sFields = setting.getFields();
		long dataSize = collection.getDataSize();
		CollectionStorage collectionStorage = storage.getCollectionStorage(collectionId);
		
		EzyArray answerItems = EzyEntityFactory.newArray();
		synchronized (collection) {
			for(int i = 0 ; i < data.size() ; ++i) {
				EzyObject answerItem = EzyEntityFactory.newObject();
				EzyObject item = data.get(i);
				Comparable id = item.get(Constants.FIELD_ID);
				if(id != null) {
					Record existed = collection.findById(id);
					if(existed != null) {
						answerItem.put(Constants.RESULT_FIELD_EXISTED, true);
						answerItems.add(id);
						continue;
					}
				}
				else {
					while(true) {
						id = UUID.randomUUID();
						Record existed = collection.findById(id);
						if(existed == null)
							break;
					}
				}
				Record record = new Record(id, dataSize);
				collection.insert(record);
				collection.increaseDataSize();
				collectionStorage.storeRecord(record, sId, sFields, item);
				answerItem.put(Constants.FIELD_ID, id);
				answerItems.add(id);
			}
		}
		return answerItems;
	}
	
}
