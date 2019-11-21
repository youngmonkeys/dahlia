package com.tvd12.dahlia.core.codec;

import java.io.IOException;

import com.tvd12.dahlia.core.io.FileProxy;
import com.tvd12.dahlia.core.setting.FieldSetting;

public final class FieldLongWriter extends FieldAbstractWriter<Long> {

	private static final FieldLongWriter INSTANCE = new FieldLongWriter();
	
	private FieldLongWriter() {}
	
	public static FieldLongWriter getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected void writeValue(
			FileProxy file, 
			FieldSetting setting, Long value) throws IOException {
		file.write(value != null ? value : 0);
	}
	
}
