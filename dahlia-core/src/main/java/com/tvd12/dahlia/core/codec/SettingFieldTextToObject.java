package com.tvd12.dahlia.core.codec;

import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_DEFAULT;

import com.tvd12.dahlia.core.setting.FieldTextSetting;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;

class SettingFieldTextToObject extends SettingFieldToObject<FieldTextSetting> {

	private static final SettingFieldTextToObject INSTANCE 
			= new SettingFieldTextToObject();

	private SettingFieldTextToObject() {}

	public static SettingFieldTextToObject getInstance() {
		return INSTANCE;
	}

	@Override
	protected EzyObjectBuilder newObjectBuilder(
			SettingFieldToObjects mappers, FieldTextSetting setting) {
		return super.newObjectBuilder(mappers, setting)
				.append(SETTING_FIELD_DEFAULT, setting.getDefaultValue());
	}
	
}