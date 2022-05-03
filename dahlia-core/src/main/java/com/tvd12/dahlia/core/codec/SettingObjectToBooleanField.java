package com.tvd12.dahlia.core.codec;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.dahlia.core.setting.FieldBooleanSetting;
import com.tvd12.ezyfox.entity.EzyObject;

final class SettingObjectToBooleanField extends SettingObjectToField {

    private static final SettingObjectToBooleanField INSTANCE = new SettingObjectToBooleanField();

    private SettingObjectToBooleanField() {}

    public static SettingObjectToBooleanField getInstance() {
        return INSTANCE;
    }

    @Override
    protected FieldBooleanSetting newSetting(SettingObjectToFields mappers, EzyObject object) {
        FieldBooleanSetting setting = new FieldBooleanSetting();
        setting.setDefaultValue(object.get(SettingFields.DEFAULT, boolean.class));
        return setting;
    }
}