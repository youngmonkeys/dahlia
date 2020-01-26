package com.tvd12.dahlia.core.codec;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.dahlia.core.setting.FieldArraySetting;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.ezyfox.entity.EzyObject;

final class SettingObjectToArrayField extends SettingObjectToField {

	private static final SettingObjectToArrayField INSTANCE = new SettingObjectToArrayField();
	
	private SettingObjectToArrayField() {}
	
	public static SettingObjectToArrayField getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected FieldArraySetting newSetting(SettingObjectToFields mappers, EzyObject object) {
		FieldArraySetting setting = new FieldArraySetting();
		setting.setMaxSize(object.get(SettingFields.MAX_SIZE, int.class));
		EzyObject itemSetting = object.get(SettingFields.ITEM, EzyObject.class);
		FieldSetting item = mappers.toSetting(itemSetting);
		setting.setItem(item);
		return setting;
	}
	
}