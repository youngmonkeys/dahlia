package com.tvd12.dahlia.core.codec;

import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_DEFAULT;

import com.tvd12.dahlia.core.setting.FieldIntegerSetting;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;

public class SettingFieldIntegerToObject extends SettingFieldToObject<FieldIntegerSetting> {

	private static final SettingFieldIntegerToObject INSTANCE 
			= new SettingFieldIntegerToObject();

	private SettingFieldIntegerToObject() {}

	public static SettingFieldIntegerToObject getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected EzyObjectBuilder newObjectBuilder(
			SettingFieldToObjects mappers, FieldIntegerSetting setting) {
		return super.newObjectBuilder(mappers, setting)
				.append(SETTING_FIELD_DEFAULT, setting.getDefaultValue());
	}

}