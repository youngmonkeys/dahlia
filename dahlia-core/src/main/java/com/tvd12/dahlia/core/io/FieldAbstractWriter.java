package com.tvd12.dahlia.core.io;

import java.io.IOException;

import com.tvd12.dahlia.core.setting.FieldSetting;

abstract class FieldAbstractWriter<T> implements FieldWriter<T> {

	@Override
	public void write(
			FieldWriters writers,
			FileProxy file, 
			FieldSetting setting, T value) throws IOException {
		writeHeader(file, setting, value);
		writeValue(file, setting, value);
	}
	
	protected void writeHeader(
			FileProxy file, 
			FieldSetting setting, T value) throws IOException {
		if(setting.isNullable()) {
			byte header = 0;
			if(value != null)
				header |= 1 << 0;
			file.writeByte(header);
		}
	}
	
	protected void writeValue(
			FieldWriters writers,
			FileProxy file, 
			FieldSetting setting, T value) throws IOException {
		writeValue(file, setting, value);
	}
	
	protected void writeValue(
			FileProxy file, 
			FieldSetting setting, T value) throws IOException {
	}
	
}
