package com.tvd12.dahlia.core.codec;

import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_TYPE;

import java.util.HashMap;
import java.util.Map;

import com.tvd12.dahlia.core.data.DataType;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.dahlia.core.setting.FieldSettingProxy;
import com.tvd12.ezyfox.entity.EzyObject;

public class SettingObjectToFields {
	
	protected final Map<DataType, SettingObjectToField> mappers;
	
	public SettingObjectToFields() {
		this.mappers = defaultMappers();
	}
	
	public FieldSettingProxy toSetting(EzyObject object) {
		DataType type = DataType.valueOf(object.get(SETTING_FIELD_TYPE));
		SettingObjectToField mapper = mappers.get(type);
		FieldSettingProxy answer = mapper.toSetting(object);
		return answer;
	}
	
	protected Map<DataType, SettingObjectToField> defaultMappers() {
		Map<DataType, SettingObjectToField> map = new HashMap<>();
//		map.put(DataType.BOOLEAN, SettingFieldBooleanToObject.getInstance());
//		map.put(DataType.INTEGER, SettingFieldIntegerToObject.getInstance());
		map.put(DataType.LONG, SettingObjectToLongField.getInstance());
//		map.put(DataType.TEXT, SettingFieldTextToObject.getInstance());
		return map;
	}
}