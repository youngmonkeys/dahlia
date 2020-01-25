package com.tvd12.dahlia.core.setting;

import java.util.Map;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.dahlia.core.data.DataType;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FieldArraySetting extends FieldEntitySetting {

	protected int maxSize;
	protected FieldSetting item;
	
	public FieldArraySetting() {
		this.maxSize = Byte.MAX_VALUE;
	}
	
	@Override
	public DataType getType() {
		return DataType.ARRAY;
	}
	
	@Override
	public Map<Object, Object> toMap() {
		Map<Object, Object> map = super.toMap();
		map.put(SettingFields.MAX_SIZE, maxSize);
		map.put(SettingFields.ITEM, item.toMap());
		return map;
	}
	
}
