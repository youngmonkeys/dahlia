package com.tvd12.dahlia.core.io;

import com.tvd12.dahlia.core.setting.FieldSetting;

import java.io.IOException;

final class FieldDoubleReader extends FieldAbstractReader<Double> {

    private static final FieldDoubleReader INSTANCE = new FieldDoubleReader();

    private FieldDoubleReader() {}

    public static FieldDoubleReader getInstance() {
        return INSTANCE;
    }

    @Override
    protected Double readValue(
        FileProxy file, FieldSetting setting) throws IOException {
        return file.readDouble();
    }
}
