package com.tvd12.dahlia.core.io;

import com.tvd12.dahlia.core.setting.FieldSetting;

import java.io.IOException;

final class FieldLongReader extends FieldAbstractReader<Long> {

    private static final FieldLongReader INSTANCE = new FieldLongReader();

    private FieldLongReader() {}

    public static FieldLongReader getInstance() {
        return INSTANCE;
    }

    @Override
    protected Long readValue(
        FileProxy file, FieldSetting setting) throws IOException {
        return file.readLong();
    }
}
