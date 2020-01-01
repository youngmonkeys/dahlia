package com.tvd12.dahlia.core.io;

import java.io.IOException;

import com.tvd12.dahlia.core.setting.FieldSetting;

public final class FieldDoubleWriter extends FieldAbstractWriter<Number> {

	private static final FieldDoubleWriter INSTANCE = new FieldDoubleWriter();
	
	private FieldDoubleWriter() {}
	
	public static FieldDoubleWriter getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected void writeValue(
			FileProxy file, 
			FieldSetting setting, Number value) throws IOException {
		file.writeDouble(value != null ? value.doubleValue() : 0.0D);
	}
	
}
