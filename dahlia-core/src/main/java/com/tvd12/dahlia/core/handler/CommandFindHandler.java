package com.tvd12.dahlia.core.handler;

import static com.tvd12.ezyfox.factory.EzyEntityFactory.EMPTY_OBJECT;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import com.tvd12.dahlia.constant.OptionFields;
import com.tvd12.dahlia.core.command.CommandFind;
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
import com.tvd12.ezyfox.util.EzyWrap;

@SuppressWarnings({"rawtypes", "unchecked"})
public class CommandFindHandler extends CommandQueryHandler<CommandFind> {

	@Override
	public Object handle(CommandFind command) {
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
		
		EzyObject options = command.getOptions();
		int skip = options.get(OptionFields.SKIP, int.class, 0);
		int limit = options.get(OptionFields.LIMIT, int.class, 25);
		EzyObject sortBy = options.get(OptionFields.SORT, EzyObject.class, EMPTY_OBJECT);
		if(sortBy.isEmpty()) {
			EzyArray answer = EzyEntityFactory.newArray();
			EzyWrap<Integer> count = new EzyWrap<>(0);
			synchronized (collection) {
				collection.forEach(new RecordConsumer() {
					@Override
					public void accept(Record r) {
						EzyObject value = collectionStorage.readRecord(r, sId, sFields);
						boolean accepted = predicate.test(value);
						if(accepted) {
							int currentCount = count.getValue();
							if(currentCount >= skip)
								answer.add(value);
							count.setValue(currentCount + 1);
						}
					}
					@Override
					public boolean next() {
						int currentSize = answer.size();
						return currentSize < limit;
					}
				});
			}
			return answer;
		}
		else {
			List<EzyObject> found = new ArrayList<>();
			synchronized (collection) {
				collection.forEach(new RecordConsumer() {
					@Override
					public void accept(Record r) {
						EzyObject value = collectionStorage.readRecord(r, sId, sFields);
						boolean accepted = predicate.test(value);
						if(accepted)
							found.add(value);
					}
				});
			}
			found.sort(sortByComparator(sortBy));
			return getResult(found, skip, limit);
		}
	}
	
	protected EzyArray getResult(List<EzyObject> list, int skip, int limit) {
		EzyArray answer = EzyEntityFactory.newArray();
		if(list.size() < skip)
			return answer;
		int min = Math.min(skip + limit, list.size());
		for(int i = skip ; i < min ; ++i)
			answer.add(list.get(i));
		return answer;
	}
	
	public Comparator<EzyObject> sortByComparator(EzyObject sortBy) {
		Set<Map.Entry<String, Boolean>> entrySet = (Set)sortBy.entrySet();
		if(entrySet.size() == 1) {
			for(Map.Entry<String, Boolean> e : entrySet) {
				String field = e.getKey();
				Boolean asc = e.getValue();
				if(asc) {
					return (a, b) -> {
						Comparable v1 = a.get(field);
						Comparable v2 = b.get(field);
						return v1.compareTo(v2);
					};
				}
				else {
					return (a, b) -> {
						Comparable v1 = a.get(field);
						Comparable v2 = b.get(field);
						return v2.compareTo(v1);
					};
				}
			}
		}
		List<Comparator<EzyObject>> comparators = new ArrayList<>();
		for(Map.Entry<String, Boolean> e : entrySet) {
			Comparator<EzyObject> comparator = null;
			String field = e.getKey();
			Boolean asc = e.getValue();
			if(asc) {
				comparator = (a, b) -> {
					Comparable v1 = a.get(field);
					Comparable v2 = b.get(field);
					return v1.compareTo(v2);
				};
			}
			else {
				comparator = (a, b) -> {
					Comparable v1 = a.get(field);
					Comparable v2 = b.get(field);
					return v2.compareTo(v1);
				};
			}
			comparators.add(comparator);
		}
		return (a, b) -> {
			for(Comparator<EzyObject> comparator : comparators) {
				int result = comparator.compare(a, b);
				if(result != 0)
					return result;
			}
			return 0;
		};
	}
	
}
