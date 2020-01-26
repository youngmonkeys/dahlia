package com.tvd12.dahlia.core.codec;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.dahlia.core.setting.FieldFloatSetting;
import com.tvd12.ezyfox.entity.EzyObject;

final class SettingObjectToFloatField extends SettingObjectToField {

	private static final SettingObjectToFloatField INSTANCE = new SettingObjectToFloatField();
	
	private SettingObjectToFloatField() {}
	
	public static SettingObjectToFloatField getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected FieldFloatSetting newSetting(SettingObjectToFields mappers, EzyObject object) {
		FieldFloatSetting setting = new FieldFloatSetting();
		setting.setDefaultValue(object.get(SettingFields.DEFAULT, float.class));
		return setting;
	}
	
}