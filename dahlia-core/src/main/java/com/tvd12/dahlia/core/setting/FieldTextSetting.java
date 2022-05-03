package com.tvd12.dahlia.core.setting;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.dahlia.core.data.DataType;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class FieldTextSetting extends FieldSetting {

    protected int maxSize;
    protected String defaultValue;

    public FieldTextSetting() {
        this.maxSize = 255;
    }

    @Override
    public DataType getType() {
        return DataType.TEXT;
    }

    @Override
    public Map<Object, Object> toMap() {
        Map<Object, Object> map = super.toMap();
        map.put(SettingFields.MAX_SIZE, maxSize);
        map.put(SettingFields.DEFAULT, defaultValue);
        return map;
    }
}
