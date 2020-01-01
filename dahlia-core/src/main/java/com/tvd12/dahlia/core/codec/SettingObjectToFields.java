package com.tvd12.dahlia.core.codec;

import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_NAME;
import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_TYPE;

import java.util.HashMap;
import java.util.Map;

import com.tvd12.dahlia.core.data.DataType;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;

public class SettingObjectToFields {
	
	protected final Map<DataType, SettingObjectToField> mappers;
	
	public SettingObjectToFields() {
		this.mappers = defaultMappers();
	}
	
	public FieldSetting toSetting(EzyObject object) {
		DataType type = DataType.valueOf(object.get(SETTING_FIELD_TYPE));
		SettingObjectToField mapper = mappers.get(type);
		FieldSetting answer = mapper.toSetting(this, object);
		return answer;
	}
	
	public Map<String, FieldSetting> toFieldSettings(EzyArray array) {
		Map<String, FieldSetting> fields = new HashMap<>();
		for(int i = 0 ; i < array.size() ; ++i) {
			EzyObject object = array.get(i);
			String name = object.get(SETTING_FIELD_NAME);
			FieldSetting field = toSetting(object);
			fields.put(name, field);
		}
		return fields;
	}
	
	protected Map<DataType, SettingObjectToField> defaultMappers() {
		Map<DataType, SettingObjectToField> map = new HashMap<>();
		map.put(DataType.BOOLEAN, SettingObjectToBooleanField.getInstance());
		map.put(DataType.BYTE, SettingObjectToByteField.getInstance());
		map.put(DataType.DOUBLE, SettingObjectToDoubleField.getInstance());
		map.put(DataType.FLOAT, SettingObjectToFloatField.getInstance());
		map.put(DataType.INTEGER, SettingObjectToIntegerField.getInstance());
		map.put(DataType.LONG, SettingObjectToLongField.getInstance());
		map.put(DataType.SHORT, SettingObjectToShortField.getInstance());
		map.put(DataType.TEXT, SettingObjectToTextField.getInstance());
		map.put(DataType.ARRAY, SettingObjectToArrayField.getInstance());
		map.put(DataType.OBJECT, SettingObjectToObjectField.getInstance());
		return map;
	}
}