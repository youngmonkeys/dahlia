package com.tvd12.dahlia.core.codec;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.dahlia.core.setting.FieldByteSetting;
import com.tvd12.ezyfox.entity.EzyObject;

final class SettingObjectToByteField extends SettingObjectToField {

	private static final SettingObjectToByteField INSTANCE = new SettingObjectToByteField();
	
	private SettingObjectToByteField() {}
	
	public static SettingObjectToByteField getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected FieldByteSetting newSetting(SettingObjectToFields mappers, EzyObject object) {
		FieldByteSetting setting = new FieldByteSetting();
		setting.setDefaultValue(object.get(SettingFields.DEFAULT, byte.class));
		return setting;
	}
	
}