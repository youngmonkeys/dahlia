package com.tvd12.dahlia.core.handler;

import java.util.Map;
import java.util.function.Predicate;

import com.tvd12.dahlia.core.command.CommandDelete;
import com.tvd12.dahlia.core.constant.Constants;
import com.tvd12.dahlia.core.entity.Collection;
import com.tvd12.dahlia.core.entity.Record;
import com.tvd12.dahlia.core.function.RecordConsumer;
import com.tvd12.dahlia.core.setting.CollectionSetting;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.dahlia.core.storage.CollectionStorage;
import com.tvd12.dahlia.exception.CollectionNotFoundException;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;

public class CommandDeleteHandler extends CommandQueryHandler<CommandDelete> {

	@Override
	public Object handle(CommandDelete command) {
		int collectionId = command.getCollectionId();
		Collection collection = databases.getCollection(collectionId);
		if(collection == null)
			throw new CollectionNotFoundException(collectionId);
		
		EzyObject query = command.getQuery();
		Predicate<EzyObject> predicate = queryToPredicate.toPredicate(query);
		
		CollectionSetting setting = collection.getSetting();
		CollectionStorage collectionStorage = storage.getCollectionStorage(collectionId);
		FieldSetting sId = setting.getId();
		Map<String, FieldSetting> sFields = setting.getFields();
		
		EzyArray deletedItems = EzyEntityFactory.newArray();
		synchronized (collection) {
			collection.forEach(new RecordConsumer() {
				@Override
				public void accept(Record r) {
					EzyObject value = collectionStorage.readRecord(r, sId, sFields);
					boolean accepted = predicate.test(value);
					if(accepted) {
						EzyObject deletedItem = EzyEntityFactory.newObject();
						deletedItem.put(Constants.FIELD_ID, r.getId());
						deletedItems.add(deletedItem);
					}
				}
			});
			for(int i = 0 ; i < deletedItems.size() ; ++i) {
				EzyObject deletedItem = deletedItems.get(i);
				Comparable id = deletedItem.get(Constants.FIELD_ID);
				collection.remove(id);
			}
		}
		return deletedItems;
	}
	
}
