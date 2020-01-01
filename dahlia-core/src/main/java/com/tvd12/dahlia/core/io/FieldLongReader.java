package com.tvd12.dahlia.core.io;

import java.io.IOException;

import com.tvd12.dahlia.core.setting.FieldSetting;

public final class FieldLongReader extends FieldAbstractReader<Long> {

	private static final FieldLongReader INSTANCE = new FieldLongReader();
	
	private FieldLongReader() {}
	
	public static FieldLongReader getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected Long readValue(
			FileProxy file, FieldSetting setting) throws IOException {
		return file.readLong();
	}
	
}
