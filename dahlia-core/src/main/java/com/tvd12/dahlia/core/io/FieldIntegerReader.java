package com.tvd12.dahlia.core.io;

import com.tvd12.dahlia.core.setting.FieldSetting;

import java.io.IOException;

final class FieldIntegerReader extends FieldAbstractReader<Integer> {

    private static final FieldIntegerReader INSTANCE = new FieldIntegerReader();

    private FieldIntegerReader() {}

    public static FieldIntegerReader getInstance() {
        return INSTANCE;
    }

    @Override
    protected Integer readValue(
        FileProxy file, FieldSetting setting) throws IOException {
        return file.readInt();
    }
}
