package com.tvd12.dahlia.core.setting;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.dahlia.core.data.DataType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Setter
@Getter
public class FieldBigDecimalSetting extends FieldSetting {

    protected BigDecimal defaultValue;

    @Override
    public DataType getType() {
        return DataType.BIGDECIMAL;
    }

    @Override
    public Map<Object, Object> toMap() {
        Map<Object, Object> map = super.toMap();
        map.put(SettingFields.DEFAULT, defaultValue);
        return map;
    }
}
