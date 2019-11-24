package com.tvd12.dahlia.core.codec;

import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_ID;

import com.tvd12.dahlia.core.setting.DatabaseSetting;
import com.tvd12.ezyfox.codec.EzyObjectSerializer;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;

public class SettingDatabaseSerializer implements SettingSerializer<DatabaseSetting> {

	protected final EzyObjectSerializer objectSerializer;
	
	public SettingDatabaseSerializer(EzyObjectSerializer objectSerializer) {
		this.objectSerializer = objectSerializer;
	}
	
	@Override
	public byte[] serialize(DatabaseSetting setting) {
		EzyObject object = databaseToObject(setting);
		byte[] bytes = objectSerializer.serialize(object);
		return bytes;
	}

	protected EzyObject databaseToObject(DatabaseSetting setting) {
		return EzyEntityFactory.newObjectBuilder()
				.append(SETTING_FIELD_ID, setting.getDatabaseId())
				.build();
	}

}
