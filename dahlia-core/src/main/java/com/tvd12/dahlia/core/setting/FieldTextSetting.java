package com.tvd12.dahlia.core.setting;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FieldTextSetting extends FieldSetting {

	protected String defaultValue;
	protected int maxSize;

}
