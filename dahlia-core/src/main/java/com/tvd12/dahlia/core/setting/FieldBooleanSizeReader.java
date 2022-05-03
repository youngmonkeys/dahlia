package com.tvd12.dahlia.core.setting;

final class FieldBooleanSizeReader extends FieldSizeReader<FieldBooleanSetting> {

    private static final FieldBooleanSizeReader INSTANCE = new FieldBooleanSizeReader();

    private FieldBooleanSizeReader() {}

    public static FieldBooleanSizeReader getInstance() {
        return INSTANCE;
    }

    @Override
    protected int readDataSize(FieldBooleanSetting setting) {
        return 1;
    }
}