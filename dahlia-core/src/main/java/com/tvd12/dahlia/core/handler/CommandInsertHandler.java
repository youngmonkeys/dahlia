package com.tvd12.dahlia.core.handler;

import java.util.Map;

import com.tvd12.dahlia.core.command.Insert;
import com.tvd12.dahlia.core.constant.Constants;
import com.tvd12.dahlia.core.entity.Collection;
import com.tvd12.dahlia.core.entity.Record;
import com.tvd12.dahlia.core.setting.CollectionSetting;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.dahlia.core.storage.CollectionStorage;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;

public class CommandInsertHandler extends CommandAbstractHandler<Insert> {

	@Override
	public Object handle(Insert command) {
		int collectionId = command.getCollectionId();
		EzyArray data = command.getData();
		
		Collection collection = databases.getCollection(collectionId);
		CollectionSetting setting = collection.getSetting();
		FieldSetting sId = setting.getId();
		Map<String, FieldSetting> sFields = setting.getFields();
		long dataSize = collection.getDataSize();
		CollectionStorage collectionStorage = storage.getCollectionStorage(collectionId);
		
		EzyArray successIds = EzyEntityFactory.newArray();
		synchronized (collection) {
			for(int i = 0 ; i < data.size() ; ++i) {
				EzyObject item = data.get(i);
				Comparable id = item.get(Constants.FIELD_ID);
				Record existed = collection.findById(id);
				if(existed != null)
					continue;
				Record record = new Record(id, dataSize);
				collection.insert(record);
				collection.increaseDataSize();
				collectionStorage.storeRecord(record, sId, sFields, item);
				successIds.add(id);
			}
		}
		return successIds;
	}
	
}
