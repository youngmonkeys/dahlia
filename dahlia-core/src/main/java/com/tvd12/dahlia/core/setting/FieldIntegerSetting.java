package com.tvd12.dahlia.core.setting;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.dahlia.core.data.DataType;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class FieldIntegerSetting extends FieldSetting {

    protected Integer defaultValue = null;
    protected Integer maxValue = Integer.MAX_VALUE;

    @Override
    public DataType getType() {
        return DataType.INTEGER;
    }

    @Override
    public Map<Object, Object> toMap() {
        Map<Object, Object> map = super.toMap();
        map.put(SettingFields.DEFAULT, defaultValue);
        map.put(SettingFields.MAX_VALUE, maxValue);
        return map;
    }
}
