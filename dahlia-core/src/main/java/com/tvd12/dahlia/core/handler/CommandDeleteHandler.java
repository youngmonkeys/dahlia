package com.tvd12.dahlia.core.handler;

import com.tvd12.dahlia.core.command.CommandDelete;
import com.tvd12.dahlia.core.constant.Constants;
import com.tvd12.dahlia.core.entity.Collection;
import com.tvd12.dahlia.core.entity.Record;
import com.tvd12.dahlia.core.setting.CollectionSetting;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.dahlia.core.storage.CollectionStorage;
import com.tvd12.dahlia.exception.CollectionNotFoundException;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import com.tvd12.ezyfox.util.EzyPair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@SuppressWarnings("rawtypes")
public class CommandDeleteHandler extends CommandQueryHandler<CommandDelete> {

    @Override
    public Object handle(CommandDelete command) {
        int collectionId = command.getCollectionId();
        Collection collection = databases.getCollection(collectionId);
        if (collection == null) {
            throw new CollectionNotFoundException(collectionId);
        }

        EzyObject query = command.getQuery();
        Predicate<EzyObject> predicate = queryToPredicate.toPredicate(query);

        CollectionSetting setting = collection.getSetting();
        CollectionStorage collectionStorage = storage.getCollectionStorage(collectionId);
        FieldSetting settingId = setting.getId();
        Map<String, FieldSetting> settingFields = setting.getFields();

        List<EzyPair<Record, EzyObject>> deletedItems = new ArrayList<>();
        //noinspection SynchronizationOnLocalVariableOrMethodParameter
        synchronized (collection) {
            collection.forEach(r -> {
                EzyObject value = collectionStorage.readRecord(r, settingId, settingFields);
                boolean accepted = predicate.test(value);
                if (accepted) {
                    deletedItems.add(new EzyPair<>(r, value));
                }
            });
            for (EzyPair<Record, EzyObject> pair : deletedItems) {
                Record deletedRecord = pair.getKey();
                EzyObject deletedValue = pair.getValue();
                Comparable id = deletedRecord.getId();
                collection.remove(id);
                deletedRecord.setAlive(false);
                collectionStorage.storeRecord(deletedRecord, settingId, settingFields, deletedValue);
            }
        }
        EzyArray answer = EzyEntityFactory.newArray();
        for (EzyPair<Record, EzyObject> pair : deletedItems) {
            EzyObject answerItem = EzyEntityFactory.newObject();
            EzyObject updateItem = pair.getValue();
            answerItem.put(Constants.FIELD_ID, updateItem.get(Constants.FIELD_ID));
        }
        return answer;
    }
}
