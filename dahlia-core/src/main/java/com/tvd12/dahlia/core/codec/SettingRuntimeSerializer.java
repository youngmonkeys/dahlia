package com.tvd12.dahlia.core.codec;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.dahlia.core.setting.RuntimeSetting;
import com.tvd12.ezyfox.codec.EzyObjectSerializer;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;

public class SettingRuntimeSerializer implements SettingSerializer<RuntimeSetting> {

	protected final EzyObjectSerializer objectSerializer;
	
	public SettingRuntimeSerializer(EzyObjectSerializer objectSerializer) {
		this.objectSerializer = objectSerializer;
	}
	
	@Override
	public byte[] serialize(RuntimeSetting setting) {
		EzyObject object = settingToObject(setting);
		byte[] bytes = objectSerializer.serialize(object);
		return bytes;
	}

	protected EzyObject settingToObject(RuntimeSetting setting) {
		return EzyEntityFactory.newObjectBuilder()
				.append(SettingFields.MAX_DATABASE_ID, setting.getMaxDatabaseId())
				.append(SettingFields.MAX_COLLECTION_ID, setting.getMaxCollectionId())
				.build();
	}
	
}
