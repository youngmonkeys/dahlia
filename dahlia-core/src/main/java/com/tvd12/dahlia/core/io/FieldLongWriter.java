package com.tvd12.dahlia.core.io;

import com.tvd12.dahlia.core.setting.FieldSetting;

import java.io.IOException;

final class FieldLongWriter extends FieldAbstractWriter<Number> {

    private static final FieldLongWriter INSTANCE = new FieldLongWriter();

    private FieldLongWriter() {}

    public static FieldLongWriter getInstance() {
        return INSTANCE;
    }

    @Override
    protected void writeValue(
        FileProxy file,
        FieldSetting setting, Number value) throws IOException {
        file.writeLong(value != null ? value.longValue() : 0L);
    }
}
