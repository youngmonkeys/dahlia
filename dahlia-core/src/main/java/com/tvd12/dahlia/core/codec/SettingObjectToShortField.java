package com.tvd12.dahlia.core.codec;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.dahlia.core.setting.FieldShortSetting;
import com.tvd12.ezyfox.entity.EzyObject;

final class SettingObjectToShortField extends SettingObjectToField {

	private static final SettingObjectToShortField INSTANCE = new SettingObjectToShortField();
	
	private SettingObjectToShortField() {}
	
	public static SettingObjectToShortField getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected FieldShortSetting newSetting(SettingObjectToFields mappers, EzyObject object) {
		FieldShortSetting setting = new FieldShortSetting();
		setting.setDefaultValue(object.get(SettingFields.DEFAULT, short.class));
		return setting;
	}
	
}