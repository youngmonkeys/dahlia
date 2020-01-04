package com.tvd12.dahlia.core.codec;

import com.tvd12.dahlia.core.setting.FieldUuidSetting;

class SettingFieldUuidToObject extends SettingFieldToObject<FieldUuidSetting> {

	private static final SettingFieldUuidToObject INSTANCE 
			= new SettingFieldUuidToObject();

	private SettingFieldUuidToObject() {}

	public static SettingFieldUuidToObject getInstance() {
		return INSTANCE;
	}

}