package com.tvd12.dahlia.core.io;

import java.io.IOException;

import com.tvd12.dahlia.core.setting.FieldSetting;

final class FieldShortReader extends FieldAbstractReader<Short> {

	private static final FieldShortReader INSTANCE = new FieldShortReader();
	
	private FieldShortReader() {}
	
	public static FieldShortReader getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected Short readValue(
			FileProxy file, FieldSetting setting) throws IOException {
		return file.readShort();
	}
	
}
