package com.tvd12.dahlia.core.io;

import java.io.IOException;
import java.util.UUID;

import com.tvd12.dahlia.core.setting.FieldSetting;

final class FieldUuidWriter extends FieldAbstractWriter<UUID> {

	private static final FieldUuidWriter INSTANCE = new FieldUuidWriter();
	
	private FieldUuidWriter() {}
	
	public static FieldUuidWriter getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected void writeValue(
			FileProxy file, 
			FieldSetting setting, UUID value) throws IOException {
		String str = value.toString();
		file.writeBytes(str.getBytes());
	}
	
}
