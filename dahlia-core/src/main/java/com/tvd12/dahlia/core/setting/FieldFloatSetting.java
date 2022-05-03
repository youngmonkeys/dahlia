package com.tvd12.dahlia.core.setting;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.dahlia.core.data.DataType;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class FieldFloatSetting extends FieldSetting {

    protected Float defaultValue;

    @Override
    public DataType getType() {
        return DataType.FLOAT;
    }

    @Override
    public Map<Object, Object> toMap() {
        Map<Object, Object> map = super.toMap();
        map.put(SettingFields.DEFAULT, defaultValue);
        return map;
    }
}
