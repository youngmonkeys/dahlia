package com.tvd12.dahlia.core.codec;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.dahlia.core.setting.FieldTextSetting;
import com.tvd12.ezyfox.entity.EzyObject;

final class SettingObjectToTextField extends SettingObjectToField {

    private static final SettingObjectToTextField INSTANCE = new SettingObjectToTextField();

    private SettingObjectToTextField() {}

    public static SettingObjectToTextField getInstance() {
        return INSTANCE;
    }

    @Override
    protected FieldTextSetting newSetting(SettingObjectToFields mappers, EzyObject object) {
        FieldTextSetting setting = new FieldTextSetting();
        setting.setMaxSize(object.get(SettingFields.MAX_SIZE, int.class));
        setting.setDefaultValue(object.get(SettingFields.DEFAULT, String.class));
        return setting;
    }
}