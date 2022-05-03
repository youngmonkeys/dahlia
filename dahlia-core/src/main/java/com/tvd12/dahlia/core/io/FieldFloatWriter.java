package com.tvd12.dahlia.core.io;

import com.tvd12.dahlia.core.setting.FieldSetting;

import java.io.IOException;

final class FieldFloatWriter extends FieldAbstractWriter<Number> {

    private static final FieldFloatWriter INSTANCE = new FieldFloatWriter();

    private FieldFloatWriter() {}

    public static FieldFloatWriter getInstance() {
        return INSTANCE;
    }

    @Override
    protected void writeValue(
        FileProxy file,
        FieldSetting setting, Number value) throws IOException {
        file.writeFloat(value != null ? value.floatValue() : 0F);
    }
}
