package com.tvd12.dahlia.core.codec;

import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_ID;

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
		setting.setDatabaseId(object.get(SETTING_FIELD_ID, int.class));
		return setting;
		
	}
	
}
