package com.tvd12.dahlia.core.codec;

import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_DEFAULT;

import com.tvd12.dahlia.core.setting.FieldFloatSetting;
import com.tvd12.ezyfox.entity.EzyObject;

public class SettingObjectToFloatField extends SettingObjectToField {

	private static final SettingObjectToFloatField INSTANCE = new SettingObjectToFloatField();
	
	private SettingObjectToFloatField() {}
	
	public static SettingObjectToFloatField getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected FieldFloatSetting newSetting(SettingObjectToFields mappers, EzyObject object) {
		FieldFloatSetting setting = new FieldFloatSetting();
		setting.setDefaultValue(object.get(SETTING_FIELD_DEFAULT, float.class));
		return setting;
	}
	
}