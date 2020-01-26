package com.tvd12.dahlia.core.codec;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.dahlia.core.setting.FieldIntegerSetting;
import com.tvd12.ezyfox.entity.EzyObject;

final class SettingObjectToIntegerField extends SettingObjectToField {

	private static final SettingObjectToIntegerField INSTANCE = new SettingObjectToIntegerField();
	
	private SettingObjectToIntegerField() {}
	
	public static SettingObjectToIntegerField getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected FieldIntegerSetting newSetting(SettingObjectToFields mappers, EzyObject object) {
		FieldIntegerSetting setting = new FieldIntegerSetting();
		setting.setDefaultValue(object.get(SettingFields.DEFAULT, int.class));
		setting.setMaxValue(object.get(SettingFields.MAX_VALUE, int.class));
		return setting;
	}
	
}