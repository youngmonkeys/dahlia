package com.tvd12.dahlia.core.io;

import com.tvd12.dahlia.core.setting.FieldSetting;

import java.io.IOException;

final class FieldIntegerWriter extends FieldAbstractWriter<Number> {

    private static final FieldIntegerWriter INSTANCE = new FieldIntegerWriter();

    private FieldIntegerWriter() {}

    public static FieldIntegerWriter getInstance() {
        return INSTANCE;
    }

    @Override
    protected void writeValue(
        FileProxy file,
        FieldSetting setting, Number value) throws IOException {
        file.writeInt(value != null ? value.intValue() : 0);
    }
}
