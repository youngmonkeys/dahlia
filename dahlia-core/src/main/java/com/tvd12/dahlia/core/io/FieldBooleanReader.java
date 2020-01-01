package com.tvd12.dahlia.core.io;

import java.io.IOException;

import com.tvd12.dahlia.core.setting.FieldSetting;

public final class FieldBooleanReader extends FieldAbstractReader<Boolean> {

	private static final FieldBooleanReader INSTANCE = new FieldBooleanReader();
	
	private FieldBooleanReader() {}
	
	public static FieldBooleanReader getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected Boolean readValue(
			FileProxy file, FieldSetting setting) throws IOException {
		return file.readBoolean();
	}
	
}
