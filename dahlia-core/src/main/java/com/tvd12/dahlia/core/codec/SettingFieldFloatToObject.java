package com.tvd12.dahlia.core.codec;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.dahlia.core.setting.FieldFloatSetting;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;

final class SettingFieldFloatToObject extends SettingFieldToObject<FieldFloatSetting> {

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
				.append(SettingFields.DEFAULT, setting.getDefaultValue());
	}

}