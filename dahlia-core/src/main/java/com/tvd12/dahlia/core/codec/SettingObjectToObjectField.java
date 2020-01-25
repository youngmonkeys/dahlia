package com.tvd12.dahlia.core.codec;

import java.util.Map;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.dahlia.core.setting.FieldObjectSetting;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;

public class SettingObjectToObjectField extends SettingObjectToField {

	private static final SettingObjectToObjectField INSTANCE = new SettingObjectToObjectField();
	
	private SettingObjectToObjectField() {}
	
	public static SettingObjectToObjectField getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected FieldObjectSetting newSetting(SettingObjectToFields mappers, EzyObject object) {
		FieldObjectSetting setting = new FieldObjectSetting();
		EzyArray fieldSettings = object.get(SettingFields.FIELDS, EzyArray.class);
		Map<String, FieldSetting> fields = mappers.toFieldSettings(fieldSettings);
		setting.setFields(fields);
		return setting;
	}
	
}