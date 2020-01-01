package com.tvd12.dahlia.core.codec;

import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_DEFAULT;

import com.tvd12.dahlia.core.setting.FieldFloatSetting;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;

class SettingFieldFloatToObject extends SettingFieldToObject<FieldFloatSetting> {

	private static final SettingFieldFloatToObject INSTANCE 
			= new SettingFieldFloatToObject();

	private SettingFieldFloatToObject() {}

	public static SettingFieldFloatToObject getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected EzyObjectBuilder newObjectBuilder(
			SettingFieldToObjects mappers, FieldFloatSetting setting) {
		return super.newObjectBuilder(mappers, setting)
				.append(SETTING_FIELD_DEFAULT, setting.getDefaultValue());
	}

}