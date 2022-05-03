package com.tvd12.dahlia.core.codec;

import com.tvd12.dahlia.core.setting.FieldUuidSetting;
import com.tvd12.ezyfox.entity.EzyObject;

final class SettingObjectToUuidField extends SettingObjectToField {

    private static final SettingObjectToUuidField INSTANCE = new SettingObjectToUuidField();

    private SettingObjectToUuidField() {}

    public static SettingObjectToUuidField getInstance() {
        return INSTANCE;
    }

    @Override
    protected FieldUuidSetting newSetting(SettingObjectToFields mappers, EzyObject object) {
        return new FieldUuidSetting();
    }
}