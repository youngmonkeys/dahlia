package com.tvd12.dahlia.core.codec;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.dahlia.core.setting.FieldByteSetting;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;

final class SettingFieldByteToObject extends SettingFieldToObject<FieldByteSetting> {

    private static final SettingFieldByteToObject INSTANCE
        = new SettingFieldByteToObject();

    private SettingFieldByteToObject() {}

    public static SettingFieldByteToObject getInstance() {
        return INSTANCE;
    }

    @Override
    protected EzyObjectBuilder newObjectBuilder(
        SettingFieldToObjects mappers,
        FieldByteSetting setting
    ) {
        return super.newObjectBuilder(mappers, setting)
            .append(SettingFields.DEFAULT, setting.getDefaultValue());
    }

}