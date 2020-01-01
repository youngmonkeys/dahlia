package com.tvd12.dahlia.core.io;

import java.io.IOException;

import com.tvd12.dahlia.core.setting.FieldSetting;

public final class FieldIntegerReader extends FieldAbstractReader<Integer> {

	private static final FieldIntegerReader INSTANCE = new FieldIntegerReader();
	
	private FieldIntegerReader() {}
	
	public static FieldIntegerReader getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected Integer readValue(
			FileProxy file, FieldSetting setting) throws IOException {
		return file.readInt();
	}
	
}
