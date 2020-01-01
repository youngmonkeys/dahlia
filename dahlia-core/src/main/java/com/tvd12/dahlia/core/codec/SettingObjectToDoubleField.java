package com.tvd12.dahlia.core.codec;

import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_DEFAULT;

import com.tvd12.dahlia.core.setting.FieldDoubleSetting;
import com.tvd12.ezyfox.entity.EzyObject;

public class SettingObjectToDoubleField extends SettingObjectToField {

	private static final SettingObjectToDoubleField INSTANCE = new SettingObjectToDoubleField();
	
	private SettingObjectToDoubleField() {}
	
	public static SettingObjectToDoubleField getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected FieldDoubleSetting newSetting(SettingObjectToFields mappers, EzyObject object) {
		FieldDoubleSetting setting = new FieldDoubleSetting();
		setting.setDefaultValue(object.get(SETTING_FIELD_DEFAULT, double.class));
		return setting;
	}
	
}