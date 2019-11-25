package com.tvd12.dahlia.core.codec;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.tvd12.dahlia.core.data.DataType;
import com.tvd12.dahlia.core.io.FileProxy;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.ezyfox.entity.EzyObject;

public class FieldSimpleWriters implements FieldWriters {

	protected final Map<DataType, FieldWriter> writers;
	
	public FieldSimpleWriters() {
		this.writers = defaultWriters();
	}
	
	@Override
	public void write(
			FileProxy file, 
			FieldSetting setting, Object value) throws IOException {
		writeFieldName(file, setting.getName());
		writeValue(file, setting, value);
	}

	@Override
	public void write(
			FileProxy file, 
			Map<String, FieldSetting> settings, 
			EzyObject values) throws IOException {
		for(String field : settings.keySet()) {
			Object value = values.get(field);
			FieldSetting setting = settings.get(field);
			write(file, setting, value);
		}
	}
	
	protected void writeValue(
			FileProxy file, 
			FieldSetting setting, Object value) throws IOException {
		FieldWriter writer = writers.get(setting.getType());
		writer.write(this, file, setting, value);
	}
	
	protected void writeFieldName(
			FileProxy file, String fieldName) throws IOException {
		byte[] bytes = fieldName.getBytes();
		file.writeByte((byte)bytes.length);
		file.writeBytes(bytes);
	}
	
	protected Map<DataType, FieldWriter> defaultWriters() {
		Map<DataType, FieldWriter> map = new HashMap<>();
		map.put(DataType.LONG, FieldLongWriter.getInstance());
		return map;
	}

}
