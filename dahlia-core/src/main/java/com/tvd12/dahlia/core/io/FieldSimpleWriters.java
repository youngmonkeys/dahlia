package com.tvd12.dahlia.core.io;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.tvd12.dahlia.core.data.DataType;
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
		write(file, setting, value);
	}
	
	@Override
	public void write(FileProxy file, 
			FieldSetting setting, Object value) throws IOException {
		FieldWriter writer = writers.get(setting.getType());
		writer.write(this, file, setting, value);
	}

	@Override
	public void write(
			FileProxy file, 
			Map<String, FieldSetting> settings, 
			EzyObject values) throws IOException {
		for(Entry<String, FieldSetting> e : settings.entrySet()) {
			Object value = values.get(e.getKey());
			write(file, e.getKey(), e.getValue(), value);
		}
	}
	
	protected void writeName(
			FileProxy file, String fieldName) throws IOException {
		byte[] bytes = fieldName.getBytes();
		file.writeByte((byte)bytes.length);
		file.writeBytes(bytes);
	}
	
	protected Map<DataType, FieldWriter> defaultWriters() {
		Map<DataType, FieldWriter> map = new HashMap<>();
		map.put(DataType.BOOLEAN, FieldBooleanWriter.getInstance());
		map.put(DataType.BYTE, FieldByteWriter.getInstance());
		map.put(DataType.DOUBLE, FieldDoubleWriter.getInstance());
		map.put(DataType.FLOAT, FieldFloatWriter.getInstance());
		map.put(DataType.INTEGER, FieldIntegerWriter.getInstance());
		map.put(DataType.LONG, FieldLongWriter.getInstance());
		map.put(DataType.SHORT, FieldShortWriter.getInstance());
		map.put(DataType.TEXT, FieldTextWriter.getInstance());
		map.put(DataType.ARRAY, FieldArrayWriter.getInstance());
		map.put(DataType.OBJECT, FieldObjectWriter.getInstance());
		map.put(DataType.UUID, FieldUuidWriter.getInstance());
		map.put(DataType.BIGDECIMAL, FieldBigDecimalWriter.getInstance());
		return map;
	}

}
