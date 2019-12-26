package com.tvd12.dahlia.core.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import com.tvd12.dahlia.core.command.Find;
import com.tvd12.dahlia.core.entity.Collection;
import com.tvd12.dahlia.core.exception.CollectionNotFoundException;
import com.tvd12.dahlia.core.setting.CollectionSetting;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.dahlia.core.storage.CollectionStorage;
import com.tvd12.ezyfox.entity.EzyObject;

public class CommandFindHandler extends CommandQueryHandler<Find> {

	@Override
	public Object handle(Find command) {
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
		
		List<EzyObject> answer = new ArrayList<>();
		synchronized (collection) {
			collection.forEach(r -> {
				EzyObject value = collectionStorage.readRecord(r, sId, sFields);
				boolean accepted = predicate.test(value);
				if(accepted)
					answer.add(value);
			});
		}
		return answer;
	}
	
}
