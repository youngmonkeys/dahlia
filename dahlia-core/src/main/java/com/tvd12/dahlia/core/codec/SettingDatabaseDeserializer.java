package com.tvd12.dahlia.core.codec;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.dahlia.core.setting.DatabaseSetting;
import com.tvd12.ezyfox.codec.EzyObjectDeserializer;
import com.tvd12.ezyfox.entity.EzyObject;

public class SettingDatabaseDeserializer
    implements SettingDeserializer<DatabaseSetting> {

    protected final EzyObjectDeserializer objectDeserializer;

    public SettingDatabaseDeserializer(EzyObjectDeserializer objectDeserializer) {
        this.objectDeserializer = objectDeserializer;
    }

    @Override
    public DatabaseSetting deserialize(byte[] bytes) {
        EzyObject object = objectDeserializer.deserialize(bytes);
        DatabaseSetting setting = new DatabaseSetting();
        setting.setDatabaseId(object.get(SettingFields.ID, int.class));
        return setting;
    }
}
