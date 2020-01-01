package com.tvd12.dahlia.core.codec;

import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_FIELDS;

import com.tvd12.dahlia.core.setting.FieldObjectSetting;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;

class SettingFieldObjectToObject extends SettingFieldToObject<FieldObjectSetting> {

	private static final SettingFieldObjectToObject INSTANCE 
			= new SettingFieldObjectToObject();

	private SettingFieldObjectToObject() {}

	public static SettingFieldObjectToObject getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected EzyObjectBuilder newObjectBuilder(
			SettingFieldToObjects mappers, FieldObjectSetting setting) {
		return super.newObjectBuilder(mappers, setting)
				.append(SETTING_FIELD_FIELDS, mappers.toArray(setting.getFields()));
	}

}