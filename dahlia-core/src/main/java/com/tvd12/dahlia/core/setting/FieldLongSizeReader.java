package com.tvd12.dahlia.core.setting;

final class FieldLongSizeReader extends FieldSizeReader<FieldLongSetting> {

    private static final FieldLongSizeReader INSTANCE = new FieldLongSizeReader();

    private FieldLongSizeReader() {}

    public static FieldLongSizeReader getInstance() {
        return INSTANCE;
    }

    @Override
    protected int readDataSize(FieldLongSetting setting) {
        return Long.BYTES;
    }
}