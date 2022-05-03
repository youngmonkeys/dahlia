package com.tvd12.dahlia.core.io;

import com.tvd12.dahlia.core.setting.FieldSetting;
import com.tvd12.ezyfox.io.EzyStrings;

import java.io.IOException;

final class FieldTextReader extends FieldAbstractReader<String> {

    private static final FieldTextReader INSTANCE = new FieldTextReader();

    private FieldTextReader() {}

    public static FieldTextReader getInstance() {
        return INSTANCE;
    }

    @Override
    protected String readValue(
        FileProxy file, FieldSetting setting) throws IOException {
        int length = file.readInt();
        byte[] bytes = file.readBytes(length);
        return EzyStrings.newUtf(bytes);
    }
}
