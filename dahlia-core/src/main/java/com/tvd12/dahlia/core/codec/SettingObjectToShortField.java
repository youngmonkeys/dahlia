package com.tvd12.dahlia.core.codec;

import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_DEFAULT;

import com.tvd12.dahlia.core.setting.FieldShortSetting;
import com.tvd12.ezyfox.entity.EzyObject;

public class SettingObjectToShortField extends SettingObjectToField {

	private static final SettingObjectToShortField INSTANCE = new SettingObjectToShortField();
	
	private SettingObjectToShortField() {}
	
	public static SettingObjectToShortField getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected FieldShortSetting newSetting(SettingObjectToFields mappers, EzyObject object) {
		FieldShortSetting setting = new FieldShortSetting();
		setting.setDefaultValue(object.get(SETTING_FIELD_DEFAULT, short.class));
		return setting;
	}
	
}