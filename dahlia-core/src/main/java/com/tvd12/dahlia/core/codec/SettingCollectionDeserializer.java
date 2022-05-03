package com.tvd12.dahlia.core.codec;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.dahlia.core.setting.CollectionSetting;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.ezyfox.codec.EzyObjectDeserializer;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;

import java.util.Map;

public class SettingCollectionDeserializer
    implements SettingDeserializer<CollectionSetting> {

    protected final SettingObjectToFields objectToFields;
    protected final EzyObjectDeserializer objectDeserializer;

    public SettingCollectionDeserializer(EzyObjectDeserializer objectDeserializer) {
        this.objectDeserializer = objectDeserializer;
        this.objectToFields = new SettingObjectToFields();
    }

    @Override
    public CollectionSetting deserialize(byte[] bytes) {
        EzyObject object = objectDeserializer.deserialize(bytes);
        CollectionSetting setting = new CollectionSetting();
        setting.setCollectionId(object.get(SettingFields.ID, int.class));
        setting.setRecordSize(object.get(SettingFields.RECORD_SIZE, int.class));
        EzyArray fieldArray = object.get(SettingFields.FIELDS);
        Map<String, FieldSetting> fields = arrayToFields(fieldArray);
        setting.setFields(fields);
        return setting;

    }

    public Map<String, FieldSetting> arrayToFields(EzyArray array) {
        return objectToFields.toFieldSettings(array);
    }
}
