package com.tvd12.dahlia.core.codec;

import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_NULLABLE;

import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.ezyfox.entity.EzyObject;

public abstract class SettingObjectToField {
	
	public final FieldSetting toSetting(SettingObjectToFields mappers, EzyObject object) {
		FieldSetting setting = newSetting(mappers, object);
		setting.setNullable(object.get(SETTING_FIELD_NULLABLE));
		return setting;
	}
	
	protected abstract FieldSetting newSetting(SettingObjectToFields mappers, EzyObject object);
	
}