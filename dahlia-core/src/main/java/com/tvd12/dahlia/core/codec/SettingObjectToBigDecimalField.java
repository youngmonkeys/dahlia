package com.tvd12.dahlia.core.codec;

import java.math.BigDecimal;

import com.tvd12.dahlia.constant.SettingFields;
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
		setting.setDefaultValue(object.get(SettingFields.DEFAULT, BigDecimal.class));
		return setting;
	}
	
}