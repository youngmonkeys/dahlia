package com.tvd12.dahlia.core.codec;

import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_NAME;
import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_NULLABLE;

import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.ezyfox.entity.EzyObject;

public abstract class SettingObjectToField<S extends FieldSetting> {
	
	public final S toSetting(EzyObject object) {
		S setting = newSetting(object);
		setting.setName(object.get(SETTING_FIELD_NAME));
		setting.setNullable(object.get(SETTING_FIELD_NULLABLE));
		return setting;
	}
	
	protected abstract S newSetting(EzyObject object);
	
}