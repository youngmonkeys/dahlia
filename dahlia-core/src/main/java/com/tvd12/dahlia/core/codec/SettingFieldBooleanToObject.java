package com.tvd12.dahlia.core.codec;

import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_DEFAULT;

import com.tvd12.dahlia.core.setting.FieldBooleanSetting;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;

class SettingFieldBooleanToObject extends SettingFieldToObject<FieldBooleanSetting> {

	private static final SettingFieldBooleanToObject INSTANCE 
			= new SettingFieldBooleanToObject();

	private SettingFieldBooleanToObject() {}

	public static SettingFieldBooleanToObject getInstance() {
		return INSTANCE;
	}

	@Override
	protected EzyObjectBuilder newObjectBuilder(
			SettingFieldToObjects mappers, FieldBooleanSetting setting) {
		return super.newObjectBuilder(mappers, setting)
				.append(SETTING_FIELD_DEFAULT, setting.getDefaultValue());
	}
}