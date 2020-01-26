package com.tvd12.dahlia.core.codec;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.dahlia.core.setting.FieldLongSetting;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;

final class SettingFieldLongToObject extends SettingFieldToObject<FieldLongSetting> {

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
				.append(SettingFields.DEFAULT, setting.getDefaultValue());
	}

}