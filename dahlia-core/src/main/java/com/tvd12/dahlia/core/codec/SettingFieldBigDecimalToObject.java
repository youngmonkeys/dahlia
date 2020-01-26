package com.tvd12.dahlia.core.codec;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.dahlia.core.setting.FieldBigDecimalSetting;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;

final class SettingFieldBigDecimalToObject extends SettingFieldToObject<FieldBigDecimalSetting> {

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
				.append(SettingFields.DEFAULT, setting.getDefaultValue());
	}

}