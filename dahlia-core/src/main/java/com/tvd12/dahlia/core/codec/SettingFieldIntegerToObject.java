package com.tvd12.dahlia.core.codec;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.dahlia.core.setting.FieldIntegerSetting;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;

final class SettingFieldIntegerToObject extends SettingFieldToObject<FieldIntegerSetting> {

    private static final SettingFieldIntegerToObject INSTANCE
        = new SettingFieldIntegerToObject();

    private SettingFieldIntegerToObject() {}

    public static SettingFieldIntegerToObject getInstance() {
        return INSTANCE;
    }

    @Override
    protected EzyObjectBuilder newObjectBuilder(
        SettingFieldToObjects mappers,
        FieldIntegerSetting setting
    ) {
        return super.newObjectBuilder(mappers, setting)
            .append(SettingFields.DEFAULT, setting.getDefaultValue())
            .append(SettingFields.MAX_VALUE, setting.getMaxValue());
    }
}