package com.tvd12.dahlia.core.io;

import java.io.IOException;

import com.tvd12.dahlia.core.setting.FieldSetting;

public final class FieldByteReader extends FieldAbstractReader<Byte> {

	private static final FieldByteReader INSTANCE = new FieldByteReader();
	
	private FieldByteReader() {}
	
	public static FieldByteReader getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected Byte readValue(
			FileProxy file, FieldSetting setting) throws IOException {
		return file.readByte();
	}
	
}
