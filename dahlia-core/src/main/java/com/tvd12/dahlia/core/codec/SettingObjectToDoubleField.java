package com.tvd12.dahlia.core.codec;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.dahlia.core.setting.FieldDoubleSetting;
import com.tvd12.ezyfox.entity.EzyObject;

final class SettingObjectToDoubleField extends SettingObjectToField {

	private static final SettingObjectToDoubleField INSTANCE = new SettingObjectToDoubleField();
	
	private SettingObjectToDoubleField() {}
	
	public static SettingObjectToDoubleField getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected FieldDoubleSetting newSetting(SettingObjectToFields mappers, EzyObject object) {
		FieldDoubleSetting setting = new FieldDoubleSetting();
		setting.setDefaultValue(object.get(SettingFields.DEFAULT, double.class));
		return setting;
	}
	
}