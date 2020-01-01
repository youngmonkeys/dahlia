package com.tvd12.dahlia.core.codec;

import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_NULLABLE;
import static com.tvd12.dahlia.core.constant.Constants.SETTING_FIELD_TYPE;

import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;

public abstract class SettingFieldToObject<S extends FieldSetting> {

	public final EzyObject toObject(SettingFieldToObjects mappers, S setting) {
		return newObjectBuilder(mappers, setting)
		        .append(SETTING_FIELD_TYPE, setting.getType())
		        .append(SETTING_FIELD_NULLABLE, setting.isNullable())
		        .build();
	}

	protected EzyObjectBuilder newObjectBuilder(SettingFieldToObjects mappers, S setting) {
		return EzyEntityFactory.newObjectBuilder();
	}

}