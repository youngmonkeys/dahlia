package com.tvd12.dahlia.core.codec;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.dahlia.core.setting.FieldShortSetting;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;

final class SettingFieldShortToObject extends SettingFieldToObject<FieldShortSetting> {

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
				.append(SettingFields.DEFAULT, setting.getDefaultValue());
	}

}