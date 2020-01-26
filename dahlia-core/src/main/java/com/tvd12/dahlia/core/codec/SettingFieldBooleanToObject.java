package com.tvd12.dahlia.core.codec;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.dahlia.core.setting.FieldBooleanSetting;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;

final class SettingFieldBooleanToObject extends SettingFieldToObject<FieldBooleanSetting> {

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
				.append(SettingFields.DEFAULT, setting.getDefaultValue());
	}
}