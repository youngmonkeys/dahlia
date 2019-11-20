package com.tvd12.dahlia.core.codec;

import java.nio.ByteBuffer;

import com.tvd12.dahlia.core.setting.FieldSetting;

public interface DataSerializer<T> {
	
	default void serialize(
			DataSerializers serializers,
			ByteBuffer buffer, 
			FieldSetting setting, T value) {
		serialize(buffer, setting, value);
	}
	
	default void serialize(
			ByteBuffer buffer, 
			FieldSetting setting, T value) {
	}
	
}
