package com.tvd12.dahlia.math;

import java.util.HashMap;
import java.util.Map;

import com.tvd12.dahlia.constant.Keywords;

import lombok.Getter;

public enum Operation {
	
	EQ(Keywords.EQUAL),
	NEQ(Keywords.NOT_EQUAL),
	LT(Keywords.LESS_THAN),
	LTE(Keywords.LESS_THAN_EQUAL),
	GT(Keywords.GREATER_THAN),
	GTE(Keywords.GREATER_THAN_EQUAL),
	AND(Keywords.AND),
	OR(Keywords.OR),
	IN(Keywords.IN),
	NOTIN(Keywords.NOT_IN);
	
	@Getter
	private final String keyword;
	
	private final static Map<String, Operation> MAP = defaultMap(); 
	
	private Operation(String keyword) {
		this.keyword = keyword;
	}
	
	public static Operation valueOfKeyword(String keyword) {
		Operation op = MAP.get(keyword);
		return op;
	}
	
	private static Map<String, Operation> defaultMap() {
		Map<String, Operation> map = new HashMap<>();
		map.put(EQ.keyword, EQ);
		map.put(NEQ.keyword, NEQ);
		map.put(LT.keyword, LT);
		map.put(LTE.keyword, LTE);
		map.put(GT.keyword, GT);
		map.put(GTE.keyword, GTE);
		map.put(AND.keyword, AND);
		map.put(OR.keyword, OR);
		map.put(IN.keyword, IN);
		map.put(NOTIN.keyword, NOTIN);
		return map;
	}
}
