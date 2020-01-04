package com.tvd12.dahlia.core.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.tvd12.ezyfox.constant.EzyConstant;

import lombok.Getter;

@Getter
public enum DataType implements EzyConstant {

	BOOLEAN(1, "bool"),
	BYTE(2, "byte"),
	DOUBLE(3, "double"),
	FLOAT(4, "float"),
	INTEGER(5, "int"),
	LONG(6, "long"),
	SHORT(7, "short"),
	TEXT(8, "text"),
	OBJECT(9, "object"),
	ARRAY(10, "array"),
	UUID(11, "uuid"),
	BIGDECIMAL(12, "bigdecimal");
	
	private final int id;
	private final String name;
	
	public static final Map<String, DataType> MAP_BY_NAME = mapByName();
	
	private DataType(int id, String name) {
		this.id = id; 
		this.name = name;
	}
	
	public static DataType valueOfName(String name) {
		DataType answer = valueOf(name);
		if(answer != null)
			return answer;
		answer = MAP_BY_NAME.get(answer);
		return answer;
	}
	
	private static Map<String, DataType> mapByName() {
		Map<String, DataType> map = new HashMap<>();
		map.put(BOOLEAN.name, BOOLEAN);
		map.put(BYTE.name, BYTE);
		map.put(DOUBLE.name, DOUBLE);
		map.put(FLOAT.name, FLOAT);
		map.put(INTEGER.name, INTEGER);
		map.put(LONG.name, LONG);
		map.put(SHORT.name, SHORT);
		map.put(TEXT.name, TEXT);
		map.put(OBJECT.name, OBJECT);
		map.put(ARRAY.name, ARRAY);
		map.put(UUID.name, UUID);
		map.put(BIGDECIMAL.name, BIGDECIMAL);
		return Collections.unmodifiableMap(map);
	}
}
