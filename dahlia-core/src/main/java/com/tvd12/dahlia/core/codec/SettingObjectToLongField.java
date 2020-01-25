package com.tvd12.dahlia.core.codec;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.dahlia.core.setting.FieldLongSetting;
import com.tvd12.ezyfox.entity.EzyObject;

public class SettingObjectToLongField extends SettingObjectToField {

	private static final SettingObjectToLongField INSTANCE = new SettingObjectToLongField();
	
	private SettingObjectToLongField() {}
	
	public static SettingObjectToLongField getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected FieldLongSetting newSetting(SettingObjectToFields mappers, EzyObject object) {
		FieldLongSetting setting = new FieldLongSetting();
		setting.setDefaultValue(object.get(SettingFields.DEFAULT, long.class));
		return setting;
	}
	
}