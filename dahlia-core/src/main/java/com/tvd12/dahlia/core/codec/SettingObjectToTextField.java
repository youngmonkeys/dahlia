package com.tvd12.dahlia.core.codec;

import static com.tvd12.dahlia.core.constant.Constants.*;

import com.tvd12.dahlia.core.setting.FieldTextSetting;
import com.tvd12.ezyfox.entity.EzyObject;

public class SettingObjectToTextField extends SettingObjectToField {

	private static final SettingObjectToTextField INSTANCE = new SettingObjectToTextField();
	
	private SettingObjectToTextField() {}
	
	public static SettingObjectToTextField getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected FieldTextSetting newSetting(SettingObjectToFields mappers, EzyObject object) {
		FieldTextSetting setting = new FieldTextSetting();
		setting.setMaxSize(object.get(SETTING_FIELD_MAX_SIZE, int.class));
		setting.setDefaultValue(object.get(SETTING_FIELD_DEFAULT, String.class));
		return setting;
	}
	
}