package com.tvd12.dahlia.core.codec;

import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_DEFAULT;

import com.tvd12.dahlia.core.setting.FieldByteSetting;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;

class SettingFieldByteToObject extends SettingFieldToObject<FieldByteSetting> {

	private static final SettingFieldByteToObject INSTANCE 
			= new SettingFieldByteToObject();

	private SettingFieldByteToObject() {}

	public static SettingFieldByteToObject getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected EzyObjectBuilder newObjectBuilder(
			SettingFieldToObjects mappers, FieldByteSetting setting) {
		return super.newObjectBuilder(mappers, setting)
				.append(SETTING_FIELD_DEFAULT, setting.getDefaultValue());
	}

}