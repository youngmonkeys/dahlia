package com.tvd12.dahlia.core.codec;

import java.nio.ByteBuffer;

import com.tvd12.dahlia.core.setting.FieldSetting;

public abstract class DataAbstractSerializer<T> implements DataSerializer<T> {

	@Override
	public void serialize(ByteBuffer buffer, FieldSetting setting, T value) {
		serializeHeader(buffer, setting, value);
		serializeValue(buffer, setting, value);
	}
	
	protected void serializeHeader(ByteBuffer buffer, FieldSetting setting, T value) {
		if(setting.isNullable()) {
			byte header = 0;
			if(value == null)
				header |= 1 << 0;
			buffer.put(header);
		}
	}
	
	protected void serializeValue(ByteBuffer buffer, FieldSetting setting, T value) {
	}
	
}
