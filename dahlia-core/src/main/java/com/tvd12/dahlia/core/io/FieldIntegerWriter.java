package com.tvd12.dahlia.core.io;

import java.io.IOException;

import com.tvd12.dahlia.core.setting.FieldSetting;

final class FieldIntegerWriter extends FieldAbstractWriter<Number> {

	private static final FieldIntegerWriter INSTANCE = new FieldIntegerWriter();
	
	private FieldIntegerWriter() {}
	
	public static FieldIntegerWriter getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected void writeValue(
			FileProxy file, 
			FieldSetting setting, Number value) throws IOException {
		file.writeInt(value != null ? value.intValue() : 0);
	}
	
}
