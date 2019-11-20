package com.tvd12.dahlia.core.codec;

import java.nio.ByteBuffer;
import java.util.Map;

import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.ezyfox.entity.EzyObject;

public interface DataSerializers {

	void serialize(ByteBuffer buffer, FieldSetting setting, Object value);
	
	void serialize(ByteBuffer buffer, Map<String, FieldSetting> settings, EzyObject values);
	
}
