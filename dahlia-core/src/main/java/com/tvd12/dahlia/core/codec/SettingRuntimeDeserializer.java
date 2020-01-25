package com.tvd12.dahlia.core.codec;

import com.tvd12.dahlia.constant.SettingFields;
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
		setting.setMaxDatabaseId(object.get(SettingFields.MAX_DATABASE_ID, int.class));
		setting.setMaxCollectionId(object.get(SettingFields.MAX_COLLECTION_ID, int.class));
		return setting;
		
	}
	
}
