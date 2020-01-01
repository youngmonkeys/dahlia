package com.tvd12.dahlia.core.codec;

import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_DEFAULT;

import com.tvd12.dahlia.core.setting.FieldBooleanSetting;
import com.tvd12.ezyfox.entity.EzyObject;

public class SettingObjectToBooleanField extends SettingObjectToField {

	private static final SettingObjectToBooleanField INSTANCE = new SettingObjectToBooleanField();
	
	private SettingObjectToBooleanField() {}
	
	public static SettingObjectToBooleanField getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected FieldBooleanSetting newSetting(SettingObjectToFields mappers, EzyObject object) {
		FieldBooleanSetting setting = new FieldBooleanSetting();
		setting.setDefaultValue(object.get(SETTING_FIELD_DEFAULT, boolean.class));
		return setting;
	}
	
}