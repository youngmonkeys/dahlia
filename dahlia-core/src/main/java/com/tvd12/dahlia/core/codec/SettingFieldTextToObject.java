package com.tvd12.dahlia.core.codec;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.dahlia.core.setting.FieldTextSetting;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;

final class SettingFieldTextToObject extends SettingFieldToObject<FieldTextSetting> {

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
				.append(SettingFields.MAX_SIZE, setting.getMaxSize())
				.append(SettingFields.DEFAULT, setting.getDefaultValue());
	}
	
}