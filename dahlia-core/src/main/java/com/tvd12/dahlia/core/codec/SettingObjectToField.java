package com.tvd12.dahlia.core.codec;

import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_NAME;
import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_NULLABLE;

import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.dahlia.core.setting.FieldSettingProxy;
import com.tvd12.ezyfox.entity.EzyObject;

public abstract class SettingObjectToField {
	
	public final FieldSettingProxy toSetting(EzyObject object) {
		String name = object.get(SETTING_FIELD_NAME);
		FieldSetting setting = newSetting(object);
		setting.setNullable(object.get(SETTING_FIELD_NULLABLE));
		return new FieldSettingProxy(name, setting);
	}
	
	protected abstract FieldSetting newSetting(EzyObject object);
	
}