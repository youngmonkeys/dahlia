package com.tvd12.dahlia.core.handler;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import com.tvd12.dahlia.core.command.CommandCount;
import com.tvd12.dahlia.core.entity.Collection;
import com.tvd12.dahlia.core.entity.Record;
import com.tvd12.dahlia.core.function.RecordConsumer;
import com.tvd12.dahlia.core.setting.CollectionSetting;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.dahlia.core.storage.CollectionStorage;
import com.tvd12.dahlia.exception.CollectionNotFoundException;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.function.EzyPredicates;

public class CommandCountHandler extends CommandQueryHandler<CommandCount> {

	@Override
	public Object handle(CommandCount command) {
		int collectionId = command.getCollectionId();
		Collection collection = databases.getCollection(collectionId);
		if(collection == null)
			throw new CollectionNotFoundException(collectionId);
		
		EzyObject query = command.getQuery();
		Predicate<EzyObject> predicate = queryToPredicate.toPredicate(query);
		
		if(predicate == EzyPredicates.ALWAY_TRUE)
			return collection.size();
		
		CollectionSetting setting = collection.getSetting();
		CollectionStorage collectionStorage = storage.getCollectionStorage(collectionId);
		FieldSetting sId = setting.getId();
		Map<String, FieldSetting> sFields = setting.getFields();
		
		AtomicInteger count = new AtomicInteger();
		synchronized (collection) {
			collection.forEach(new RecordConsumer() {
				@Override
				public void accept(Record r) {
					EzyObject value = collectionStorage.readRecord(r, sId, sFields);
					boolean accepted = predicate.test(value);
					if(accepted)
						count.incrementAndGet();
				}
			});
		}
		return count.get();
	}
	
}
