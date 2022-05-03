package com.tvd12.dahlia.core.codec;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.dahlia.core.data.DataType;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.ezyfox.builder.EzyArrayBuilder;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

@SuppressWarnings({"rawtypes", "unchecked"})
class SettingFieldToObjects {

    protected final Map<DataType, SettingFieldToObject> mappers;

    public SettingFieldToObjects() {
        this.mappers = defaultMappers();
    }

    public EzyObject toObject(String name, FieldSetting setting) {
        EzyObject answer = toObject(setting);
        answer.put(SettingFields.NAME, name);
        return answer;
    }

    public EzyObject toObject(FieldSetting setting) {
        SettingFieldToObject mapper = mappers.get(setting.getType());
        return mapper.toObject(this, setting);
    }

    protected EzyArray toArray(Map<String, FieldSetting> fields) {
        EzyArrayBuilder builder = EzyEntityFactory.newArrayBuilder();
        for (Entry<String, FieldSetting> e : fields.entrySet()) {
            builder.append(toObject(e.getKey(), e.getValue()));
        }
        return builder.build();
    }

    protected Map<DataType, SettingFieldToObject> defaultMappers() {
        Map<DataType, SettingFieldToObject> map = new HashMap<>();
        map.put(DataType.BOOLEAN, SettingFieldBooleanToObject.getInstance());
        map.put(DataType.BYTE, SettingFieldByteToObject.getInstance());
        map.put(DataType.DOUBLE, SettingFieldDoubleToObject.getInstance());
        map.put(DataType.FLOAT, SettingFieldFloatToObject.getInstance());
        map.put(DataType.INTEGER, SettingFieldIntegerToObject.getInstance());
        map.put(DataType.LONG, SettingFieldLongToObject.getInstance());
        map.put(DataType.SHORT, SettingFieldShortToObject.getInstance());
        map.put(DataType.TEXT, SettingFieldTextToObject.getInstance());
        map.put(DataType.ARRAY, SettingFieldArrayToObject.getInstance());
        map.put(DataType.OBJECT, SettingFieldObjectToObject.getInstance());
        map.put(DataType.UUID, SettingFieldUuidToObject.getInstance());
        map.put(DataType.BIGDECIMAL, SettingFieldBigDecimalToObject.getInstance());
        return map;
    }
}