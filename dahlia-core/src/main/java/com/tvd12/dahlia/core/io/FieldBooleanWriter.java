package com.tvd12.dahlia.core.io;

import com.tvd12.dahlia.core.setting.FieldSetting;

import java.io.IOException;

final class FieldBooleanWriter extends FieldAbstractWriter<Boolean> {

    private static final FieldBooleanWriter INSTANCE = new FieldBooleanWriter();

    private FieldBooleanWriter() {}

    public static FieldBooleanWriter getInstance() {
        return INSTANCE;
    }

    @Override
    protected void writeValue(
        FileProxy file,
        FieldSetting setting, Boolean value) throws IOException {
        file.writeBoolean(value != null ? value : false);
    }
}
