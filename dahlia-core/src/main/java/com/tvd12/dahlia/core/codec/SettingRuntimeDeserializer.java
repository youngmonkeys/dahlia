package com.tvd12.dahlia.core.codec;

import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_MAX_COLLECTION_ID;
import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_MAX_DATABASE_ID;

import com.tvd12.dahlia.core.setting.RuntimeSetting;
import com.tvd12.ezyfox.codec.EzyObjectDeserializer;
import com.tvd12.ezyfox.entity.EzyObject;

public class SettingRuntimeDeserializer 
		implements SettingDeserializer<RuntimeSetting> {

	protected final EzyObjectDeserializer objectDeserializer;
	
	public SettingRuntimeDeserializer(EzyObjectDeserializer objectDeserializer) {
		this.objectDeserializer = objectDeserializer;
	}
	
	@Override
	public RuntimeSetting deserialize(byte[] bytes) {
		EzyObject object = objectDeserializer.deserialize(bytes);
		RuntimeSetting setting = new RuntimeSetting();
		setting.setMaxDatabaseId(object.get(SETTING_FIELD_MAX_DATABASE_ID, int.class));
		setting.setMaxCollectionId(object.get(SETTING_FIELD_MAX_COLLECTION_ID, int.class));
		return setting;
		
	}
	
}
