package com.tvd12.dahlia.core.io;

import com.tvd12.dahlia.core.setting.FieldSetting;

import java.io.IOException;

final class FieldFloatReader extends FieldAbstractReader<Float> {

    private static final FieldFloatReader INSTANCE = new FieldFloatReader();

    private FieldFloatReader() {}

    public static FieldFloatReader getInstance() {
        return INSTANCE;
    }

    @Override
    protected Float readValue(
        FileProxy file, FieldSetting setting) throws IOException {
        return file.readFloat();
    }
}
