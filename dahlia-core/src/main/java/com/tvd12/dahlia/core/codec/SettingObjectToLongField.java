package com.tvd12.dahlia.core.codec;

import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_DEFAULT;

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
		setting.setDefaultValue(object.get(SETTING_FIELD_DEFAULT, long.class));
		return setting;
	}
	
}