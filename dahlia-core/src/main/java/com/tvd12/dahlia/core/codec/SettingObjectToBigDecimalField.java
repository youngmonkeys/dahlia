package com.tvd12.dahlia.core.codec;

import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_DEFAULT;

import java.math.BigDecimal;

import com.tvd12.dahlia.core.setting.FieldBigDecimalSetting;
import com.tvd12.ezyfox.entity.EzyObject;

public class SettingObjectToBigDecimalField extends SettingObjectToField {

	private static final SettingObjectToBigDecimalField INSTANCE = new SettingObjectToBigDecimalField();
	
	private SettingObjectToBigDecimalField() {}
	
	public static SettingObjectToBigDecimalField getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected FieldBigDecimalSetting newSetting(SettingObjectToFields mappers, EzyObject object) {
		FieldBigDecimalSetting setting = new FieldBigDecimalSetting();
		setting.setDefaultValue(object.get(SETTING_FIELD_DEFAULT, BigDecimal.class));
		return setting;
	}
	
}