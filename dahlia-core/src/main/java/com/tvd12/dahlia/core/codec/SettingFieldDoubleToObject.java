package com.tvd12.dahlia.core.codec;

import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_DEFAULT;

import com.tvd12.dahlia.core.setting.FieldDoubleSetting;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;

class SettingFieldDoubleToObject extends SettingFieldToObject<FieldDoubleSetting> {

	private static final SettingFieldDoubleToObject INSTANCE 
			= new SettingFieldDoubleToObject();

	private SettingFieldDoubleToObject() {}

	public static SettingFieldDoubleToObject getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected EzyObjectBuilder newObjectBuilder(
			SettingFieldToObjects mappers, FieldDoubleSetting setting) {
		return super.newObjectBuilder(mappers, setting)
				.append(SETTING_FIELD_DEFAULT, setting.getDefaultValue());
	}

}