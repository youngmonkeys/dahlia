package com.tvd12.dahlia.core.codec;

import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_DEFAULT;

import com.tvd12.dahlia.core.setting.FieldLongSetting;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;

class SettingFieldLongToObject extends SettingFieldToObject<FieldLongSetting> {

	private static final SettingFieldLongToObject INSTANCE 
			= new SettingFieldLongToObject();

	private SettingFieldLongToObject() {}

	public static SettingFieldLongToObject getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected EzyObjectBuilder newObjectBuilder(
			SettingFieldToObjects mappers, FieldLongSetting setting) {
		return super.newObjectBuilder(mappers, setting)
				.append(SETTING_FIELD_DEFAULT, setting.getDefaultValue());
	}

}