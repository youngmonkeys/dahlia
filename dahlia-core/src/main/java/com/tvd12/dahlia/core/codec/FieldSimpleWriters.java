package com.tvd12.dahlia.core.codec;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
			String name,
			FieldSetting setting, Object value) throws IOException {
		writeName(file, name);
		writeValue(file, setting, value);
	}

	@Override
	public void write(
			FileProxy file, 
			Map<String, FieldSetting> settings, 
			EzyObject values) throws IOException {
		for(Entry<String, FieldSetting> e : settings.entrySet()) {
			Object value = values.get(e.getKey());
			FieldSetting setting = settings.get(e.getValue());
			write(file, e.getKey(), setting, value);
		}
	}
	
	protected void writeValue(
			FileProxy file, 
			FieldSetting setting, Object value) throws IOException {
		FieldWriter writer = writers.get(setting.getType());
		writer.write(this, file, setting, value);
	}
	
	protected void writeName(
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
