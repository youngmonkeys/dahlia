package com.tvd12.dahlia.core.codec;

import java.nio.ByteBuffer;

import com.tvd12.dahlia.core.setting.FieldSetting;

public final class DataIntegerSerializer extends DataAbstractSerializer<Integer> {

	private static final DataIntegerSerializer INSTANCE = new DataIntegerSerializer();
	
	private DataIntegerSerializer() {}
	
	public static DataIntegerSerializer getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected void serializeValue(ByteBuffer buffer, FieldSetting setting, Integer value) {
		buffer.putInt(value != null ? value : 0);
	}
	
}
