package com.tvd12.dahlia.core.io;

import java.io.IOException;

import com.tvd12.dahlia.core.setting.FieldSetting;

public final class FieldByteWriter extends FieldAbstractWriter<Number> {

	private static final FieldByteWriter INSTANCE = new FieldByteWriter();
	
	private FieldByteWriter() {}
	
	public static FieldByteWriter getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected void writeValue(
			FileProxy file, 
			FieldSetting setting, Number value) throws IOException {
		file.writeByte(value != null ? value.byteValue() : 0);
	}
	
}
