package com.tvd12.dahlia.core.codec;

import static com.tvd12.dahlia.core.constant.Constants.*;

import com.tvd12.dahlia.core.setting.FieldArraySetting;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;

class SettingFieldArrayToObject extends SettingFieldToObject<FieldArraySetting> {

	private static final SettingFieldArrayToObject INSTANCE 
			= new SettingFieldArrayToObject();

	private SettingFieldArrayToObject() {}

	public static SettingFieldArrayToObject getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected EzyObjectBuilder newObjectBuilder(
			SettingFieldToObjects mappers, FieldArraySetting setting) {
		return super.newObjectBuilder(mappers, setting)
				.append(SETTING_FIELD_MAX_SIZE, setting.getMaxSize())
				.append(SETTING_FIELD_ITEM, mappers.toObject(setting.getItem()));
	}

}