package com.tvd12.dahlia.core.codec;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.ezyfox.entity.EzyObject;

public abstract class SettingObjectToField {

    public final FieldSetting toSetting(SettingObjectToFields mappers, EzyObject object) {
        FieldSetting setting = newSetting(mappers, object);
        setting.setNullable(object.get(SettingFields.NULLABLE));
        return setting;
    }

    protected abstract FieldSetting newSetting(SettingObjectToFields mappers, EzyObject object);
}