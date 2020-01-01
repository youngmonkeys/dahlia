package com.tvd12.dahlia.core.codec;

import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_DEFAULT;

import com.tvd12.dahlia.core.setting.FieldByteSetting;
import com.tvd12.ezyfox.entity.EzyObject;

public class SettingObjectToByteField extends SettingObjectToField {

	private static final SettingObjectToByteField INSTANCE = new SettingObjectToByteField();
	
	private SettingObjectToByteField() {}
	
	public static SettingObjectToByteField getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected FieldByteSetting newSetting(SettingObjectToFields mappers, EzyObject object) {
		FieldByteSetting setting = new FieldByteSetting();
		setting.setDefaultValue(object.get(SETTING_FIELD_DEFAULT, byte.class));
		return setting;
	}
	
}