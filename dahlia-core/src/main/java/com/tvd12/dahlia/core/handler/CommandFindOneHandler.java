package com.tvd12.dahlia.core.handler;

import com.tvd12.dahlia.core.command.CommandFindOne;
import com.tvd12.dahlia.core.entity.Collection;
import com.tvd12.dahlia.core.entity.Record;
import com.tvd12.dahlia.core.function.RecordConsumer;
import com.tvd12.dahlia.core.setting.CollectionSetting;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.dahlia.core.storage.CollectionStorage;
import com.tvd12.dahlia.exception.CollectionNotFoundException;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.util.EzyWrap;

import java.util.Map;
import java.util.function.Predicate;

public class CommandFindOneHandler extends CommandQueryHandler<CommandFindOne> {

    @Override
    public Object handle(CommandFindOne command) {
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
        EzyWrap<EzyObject> ref = new EzyWrap<>();
        //noinspection SynchronizationOnLocalVariableOrMethodParameter
        synchronized (collection) {
            collection.forEach(new RecordConsumer() {
                @Override
                public void accept(Record r) {
                    EzyObject value = collectionStorage.readRecord(r, settingId, settingFields);
                    boolean accepted = predicate.test(value);
                    if (accepted) {
                        ref.setValue(value);
                    }
                }

                @Override
                public boolean next() {
                    return ref.hasNoValue();
                }
            });
        }
        return ref.getValue();
    }
}
