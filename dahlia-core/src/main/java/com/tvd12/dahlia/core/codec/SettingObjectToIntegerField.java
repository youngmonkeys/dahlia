package com.tvd12.dahlia.core.codec;

import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_DEFAULT;

import com.tvd12.dahlia.core.setting.FieldIntegerSetting;
import com.tvd12.ezyfox.entity.EzyObject;

public class SettingObjectToIntegerField extends SettingObjectToField {

	private static final SettingObjectToIntegerField INSTANCE = new SettingObjectToIntegerField();
	
	private SettingObjectToIntegerField() {}
	
	public static SettingObjectToIntegerField getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected FieldIntegerSetting newSetting(SettingObjectToFields mappers, EzyObject object) {
		FieldIntegerSetting setting = new FieldIntegerSetting();
		setting.setDefaultValue(object.get(SETTING_FIELD_DEFAULT, int.class));
		return setting;
	}
	
}