package com.tvd12.dahlia.core.codec;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.dahlia.core.data.DataType;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;

import java.util.HashMap;
import java.util.Map;

class SettingObjectToFields {

    protected final Map<DataType, SettingObjectToField> mappers;

    public SettingObjectToFields() {
        this.mappers = defaultMappers();
    }

    public FieldSetting toSetting(EzyObject object) {
        DataType type = DataType.valueOf(object.get(SettingFields.TYPE));
        SettingObjectToField mapper = mappers.get(type);
        return mapper.toSetting(this, object);
    }

    public Map<String, FieldSetting> toFieldSettings(EzyArray array) {
        Map<String, FieldSetting> fields = new HashMap<>();
        for (int i = 0; i < array.size(); ++i) {
            EzyObject object = array.get(i);
            String name = object.get(SettingFields.NAME);
            FieldSetting field = toSetting(object);
            fields.put(name, field);
        }
        return fields;
    }

    protected Map<DataType, SettingObjectToField> defaultMappers() {
        Map<DataType, SettingObjectToField> map = new HashMap<>();
        map.put(DataType.BOOLEAN, SettingObjectToBooleanField.getInstance());
        map.put(DataType.BYTE, SettingObjectToByteField.getInstance());
        map.put(DataType.DOUBLE, SettingObjectToDoubleField.getInstance());
        map.put(DataType.FLOAT, SettingObjectToFloatField.getInstance());
        map.put(DataType.INTEGER, SettingObjectToIntegerField.getInstance());
        map.put(DataType.LONG, SettingObjectToLongField.getInstance());
        map.put(DataType.SHORT, SettingObjectToShortField.getInstance());
        map.put(DataType.TEXT, SettingObjectToTextField.getInstance());
        map.put(DataType.ARRAY, SettingObjectToArrayField.getInstance());
        map.put(DataType.OBJECT, SettingObjectToObjectField.getInstance());
        map.put(DataType.UUID, SettingObjectToUuidField.getInstance());
        map.put(DataType.BIGDECIMAL, SettingObjectToBigDecimalField.getInstance());
        return map;
    }
}