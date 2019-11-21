package com.tvd12.dahlia.core.codec;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.tvd12.dahlia.core.io.FileProxy;
import com.tvd12.dahlia.core.setting.FieldSetting;

public abstract class FieldAbstractWriter<T> implements FieldWriter<T> {

	@Override
	public void write(
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
			if(value == null)
				header |= 1 << 0;
			file.write(header);
		}
	}
	
	protected void writeValue(
			FileProxy file, 
			FieldSetting setting, T value) throws IOException {
	}
	
}
