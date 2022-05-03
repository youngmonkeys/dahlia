package com.tvd12.dahlia.core.codec;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;

abstract class SettingFieldToObject<S extends FieldSetting> {

    public final EzyObject toObject(SettingFieldToObjects mappers, S setting) {
        return newObjectBuilder(mappers, setting)
            .append(SettingFields.TYPE, setting.getType())
            .append(SettingFields.NULLABLE, setting.isNullable())
            .build();
    }

    protected EzyObjectBuilder newObjectBuilder(SettingFieldToObjects mappers, S setting) {
        return EzyEntityFactory.newObjectBuilder();
    }
}