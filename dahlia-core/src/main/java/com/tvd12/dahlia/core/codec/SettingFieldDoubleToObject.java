package com.tvd12.dahlia.core.codec;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.dahlia.core.setting.FieldDoubleSetting;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;

final class SettingFieldDoubleToObject extends SettingFieldToObject<FieldDoubleSetting> {

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
				.append(SettingFields.DEFAULT, setting.getDefaultValue());
	}

}