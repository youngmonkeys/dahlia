package com.tvd12.dahlia.core.io;

import com.tvd12.dahlia.core.setting.FieldSetting;

import java.io.IOException;

final class FieldShortWriter extends FieldAbstractWriter<Number> {

    private static final FieldShortWriter INSTANCE = new FieldShortWriter();

    private FieldShortWriter() {}

    public static FieldShortWriter getInstance() {
        return INSTANCE;
    }

    @Override
    protected void writeValue(
        FileProxy file,
        FieldSetting setting, Number value) throws IOException {
        file.writeShort(value != null ? value.shortValue() : 0);
    }
}
