package com.tvd12.dahlia.core.io;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.tvd12.dahlia.core.data.DataType;
import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.ezyfox.entity.EzyObject;

public class FieldSimpleReaders implements FieldReaders {

	protected final Map<DataType, FieldReader> readers;
	
	public FieldSimpleReaders() {
		this.readers = defaultReaders();
	}
	
	@Override
	public String readName(
			FileProxy file) throws IOException {
		int fieldNameLength = file.readByte();
		String fieldName = file.readString(fieldNameLength);
		return fieldName;
	}
	
	@Override
	public Object readValue(
			FileProxy file, 
			FieldSetting setting) throws IOException {
		FieldReader reader = readers.get(setting.getType());
		Object value = reader.read(this, file, setting);
		return value;
	}
	
	@Override
	public Object read(FileProxy file, 
			FieldSetting setting) throws IOException {
		readName(file);
		return readValue(file, setting);
	}
	
	@Override
	public void read(
			FileProxy file, 
			Map<String, FieldSetting> settings, 
			EzyObject output) throws IOException {
		int readFields = 0;
		int totalFields = settings.size();
		while(readFields < totalFields) {
			String fieldName = readName(file);
			FieldSetting setting = settings.get(fieldName);
			Object value = readValue(file, setting);
			output.put(fieldName, value);
			++ readFields;
		}
	}
	
	protected Map<DataType, FieldReader> defaultReaders() {
		Map<DataType, FieldReader> map = new HashMap<>();
		map.put(DataType.BOOLEAN, FieldBooleanReader.getInstance());
		map.put(DataType.BYTE, FieldByteReader.getInstance());
		map.put(DataType.DOUBLE, FieldDoubleReader.getInstance());
		map.put(DataType.FLOAT, FieldFloatReader.getInstance());
		map.put(DataType.INTEGER, FieldIntegerReader.getInstance());
		map.put(DataType.LONG, FieldLongReader.getInstance());
		map.put(DataType.SHORT, FieldShortReader.getInstance());
		map.put(DataType.TEXT, FieldTextReader.getInstance());
		map.put(DataType.ARRAY, FieldArrayReader.getInstance());
		map.put(DataType.OBJECT, FieldObjectReader.getInstance());
		return map;
	}

}
