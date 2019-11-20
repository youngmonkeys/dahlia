package com.tvd12.dahlia.core.codec;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import com.tvd12.dahlia.core.data.DataType;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.ezyfox.entity.EzyObject;

public class DataSimpleSerializers implements DataSerializers {

	protected final Map<DataType, DataSerializer> serializers;
	
	public DataSimpleSerializers() {
		this.serializers = defaultSerializers();
	}
	
	@Override
	public void serialize(
			ByteBuffer buffer, 
			FieldSetting setting, Object value) {
		serializeFieldName(buffer, setting.getName());
		serializeValue(buffer, setting, value);
	}

	@Override
	public void serialize(
			ByteBuffer buffer, 
			Map<String, FieldSetting> settings, EzyObject values) {
		for(String field : settings.keySet()) {
			Object value = values.get(field);
			FieldSetting setting = settings.get(field);
			serialize(buffer, setting, value);
		}
	}
	
	protected void serializeValue(
			ByteBuffer buffer, 
			FieldSetting setting, Object value) {
		DataSerializer serializer = serializers.get(setting.getType());
		serializer.serialize(this, buffer, setting, value);
	}
	
	protected void serializeFieldName(
			ByteBuffer buffer, String fieldName) {
		byte[] bytes = fieldName.getBytes();
		buffer.put((byte)bytes.length);
		buffer.put(bytes);
	}
	
	protected Map<DataType, DataSerializer> defaultSerializers() {
		Map<DataType, DataSerializer> map = new HashMap<>();
		map.put(DataType.INTEGER, DataIntegerSerializer.getInstance());
		return map;
	}

}
