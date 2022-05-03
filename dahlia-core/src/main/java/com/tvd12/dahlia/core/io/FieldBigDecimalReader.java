package com.tvd12.dahlia.core.io;

import com.tvd12.dahlia.core.setting.FieldSetting;

import java.io.IOException;
import java.math.BigDecimal;

final class FieldBigDecimalReader extends FieldAbstractReader<BigDecimal> {

    private static final FieldBigDecimalReader INSTANCE = new FieldBigDecimalReader();

    private FieldBigDecimalReader() {}

    public static FieldBigDecimalReader getInstance() {
        return INSTANCE;
    }

    @Override
    protected BigDecimal readValue(
        FileProxy file, FieldSetting setting) throws IOException {
        int length = file.readByte();
        byte[] bytes = file.readBytes(length);
        return new BigDecimal(new String(bytes));
    }
}
