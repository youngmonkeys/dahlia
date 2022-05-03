package com.tvd12.dahlia.core.handler;

import com.tvd12.dahlia.core.command.CommandInsertOne;
import com.tvd12.dahlia.core.constant.Constants;
import com.tvd12.dahlia.core.entity.Collection;
import com.tvd12.dahlia.core.entity.Record;
import com.tvd12.dahlia.core.setting.CollectionSetting;
import com.tvd12.dahlia.core.storage.CollectionStorage;
import com.tvd12.dahlia.exception.DuplicatedIdException;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;

import java.util.UUID;

@SuppressWarnings("rawtypes")
public class CommandInsertOneHandler extends CommandAbstractHandler<CommandInsertOne> {

    @Override
    public Object handle(CommandInsertOne command) {
        int collectionId = command.getCollectionId();
        EzyObject data = command.getData();

        Collection collection = databases.getCollection(collectionId);
        CollectionSetting setting = collection.getSetting();
        CollectionStorage collectionStorage = storage.getCollectionStorage(collectionId);

        Comparable id = data.get(Constants.FIELD_ID);
        //noinspection SynchronizationOnLocalVariableOrMethodParameter
        synchronized (collection) {
            if (id != null) {
                Record existed = collection.findById(id);
                if (existed != null) {
                    throw new DuplicatedIdException(collection.getName(), id);
                }
            } else {
                while (true) {
                    id = UUID.randomUUID();
                    Record existed = collection.findById(id);
                    if (existed == null) {
                        break;
                    }
                }
                data.put(Constants.FIELD_ID, id);
            }
            Record record = new Record(id, collection.getDataSize());
            collection.insert(record);
            collection.increaseDataSize();
            collectionStorage.storeRecord(record, setting.getId(), setting.getFields(), data);
        }
        EzyObject answer = EzyEntityFactory.newObject();
        answer.put(Constants.FIELD_ID, id);
        return answer;
    }
}
