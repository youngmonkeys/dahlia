package com.tvd12.dahlia.core.codec;

import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_DEFAULT;

import com.tvd12.dahlia.core.setting.FieldBigDecimalSetting;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;

public class SettingFieldBigDecimalToObject extends SettingFieldToObject<FieldBigDecimalSetting> {

	private static final SettingFieldBigDecimalToObject INSTANCE 
			= new SettingFieldBigDecimalToObject();

	private SettingFieldBigDecimalToObject() {}

	public static SettingFieldBigDecimalToObject getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected EzyObjectBuilder newObjectBuilder(
			SettingFieldToObjects mappers, FieldBigDecimalSetting setting) {
		return super.newObjectBuilder(mappers, setting)
				.append(SETTING_FIELD_DEFAULT, setting.getDefaultValue());
	}

}