package com.tvd12.dahlia.core.setting;

public abstract class FieldSizeReader<S extends FieldSetting> {
	
	public final int read(FieldSizeReaders readers, S setting) {
		int size = 0;
		size += readDataSize(setting);
		size += readPropertiesSize(readers, setting);
		return size;
	}
	
	protected abstract int readDataSize(S setting);
	
	protected int readPropertiesSize(FieldSizeReaders readers, S setting) {
		return 0;
	}
	
}