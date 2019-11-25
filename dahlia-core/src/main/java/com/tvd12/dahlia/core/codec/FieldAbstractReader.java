package com.tvd12.dahlia.core.codec;

import java.io.IOException;

import com.tvd12.dahlia.core.io.FileProxy;
import com.tvd12.dahlia.core.setting.FieldSetting;

public abstract class FieldAbstractReader<T> implements FieldReader<T> {

	@Override
	public T read(
			FieldReaders readers, 
			FileProxy file, FieldSetting setting) throws IOException {
		if(setting.isNullable()) {
			byte header = file.readyByte();
			if((header & (1 << 0)) == 0)
				return null;
		}
		T value = readValue(readers, file, setting);
		return value; 
	}
	
	protected T readValue(
			FieldReaders readers,
			FileProxy file, FieldSetting setting) throws IOException {
		return readValue(file, setting);
	}
	
	protected T readValue(
			FileProxy file, FieldSetting setting) throws IOException {
		return null;
	}
	
}
