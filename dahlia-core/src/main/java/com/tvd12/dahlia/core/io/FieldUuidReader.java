package com.tvd12.dahlia.core.io;

import java.io.IOException;
import java.util.UUID;

import com.tvd12.dahlia.core.constant.Constants;
import com.tvd12.dahlia.core.setting.FieldSetting;

public final class FieldUuidReader extends FieldAbstractReader<UUID> {

	private static final FieldUuidReader INSTANCE = new FieldUuidReader();
	
	private FieldUuidReader() {}
	
	public static FieldUuidReader getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected UUID readValue(
			FileProxy file, FieldSetting setting) throws IOException {
		byte[] bytes = file.readBytes(Constants.MAX_UUID_SIZE);
		return UUID.fromString(new String(bytes));
	}
	
}
