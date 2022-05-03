package com.tvd12.dahlia.core.handler;

import com.tvd12.dahlia.core.command.CommandUpdate;
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

public class CommandUpdateHandler extends CommandQueryHandler<CommandUpdate> {

    @Override
    public Object handle(CommandUpdate command) {
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

        EzyObject update = command.getUpdate();
        List<EzyPair<Record, EzyObject>> updateItems = new ArrayList<>();
        //noinspection SynchronizationOnLocalVariableOrMethodParameter
        synchronized (collection) {
            collection.forEach(r -> {
                EzyObject value = collectionStorage.readRecord(r, settingId, settingFields);
                boolean accepted = predicate.test(value);
                if (accepted) {
                    updateItems.add(new EzyPair<>(r, value));
                }
            });
            for (EzyPair<Record, EzyObject> pair : updateItems) {
                Record record = pair.getKey();
                EzyObject updateItem = pair.getValue();
                updateItem(updateItem, update);
                collectionStorage.storeRecord(record, settingId, settingFields, updateItem);
            }
        }
        EzyArray answer = EzyEntityFactory.newArray();
        for (EzyPair<Record, EzyObject> pair : updateItems) {
            EzyObject answerItem = EzyEntityFactory.newObject();
            EzyObject updateItem = pair.getValue();
            answerItem.put(Constants.FIELD_ID, updateItem.get(Constants.FIELD_ID));
        }
        return answer;
    }

    protected void updateItem(EzyObject item, EzyObject update) {
        for (Object key : update.keySet()) {
            Object updateValue = update.get(key);
            if (updateValue instanceof EzyObject) {
                EzyObject itemValue = item.get(key);
                updateItem(itemValue, (EzyObject) updateValue);
            } else {
                item.put(key, updateValue);
            }
        }
    }
}
