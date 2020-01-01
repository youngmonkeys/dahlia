package com.tvd12.dahlia.core.codec;

import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_DEFAULT;

import com.tvd12.dahlia.core.setting.FieldShortSetting;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;

class SettingFieldShortToObject extends SettingFieldToObject<FieldShortSetting> {

	private static final SettingFieldShortToObject INSTANCE 
			= new SettingFieldShortToObject();

	private SettingFieldShortToObject() {}

	public static SettingFieldShortToObject getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected EzyObjectBuilder newObjectBuilder(
			SettingFieldToObjects mappers, FieldShortSetting setting) {
		return super.newObjectBuilder(mappers, setting)
				.append(SETTING_FIELD_DEFAULT, setting.getDefaultValue());
	}

}