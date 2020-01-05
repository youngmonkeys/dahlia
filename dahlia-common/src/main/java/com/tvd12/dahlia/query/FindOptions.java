package com.tvd12.dahlia.query;

import java.util.HashMap;
import java.util.Map;

import com.tvd12.dahlia.constant.OptionFields;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;

import lombok.Getter;

@Getter
public class FindOptions {

	protected int skip;
	protected int limit = 25;
	protected Map<String, Boolean> orderBy;
	
	public FindOptions setSkip(int skip) {
		this.skip = skip;
		return this;
	}
	
	public FindOptions setLimit(int limit) {
		this.limit = limit;
		return this;
	}
	
	public FindOptions orderBy(String field, boolean asc) {
		if(orderBy == null)
			orderBy = new HashMap<>();
		this.orderBy.put(field, asc);
		return this;
	}
	
	public Map<String, Boolean> getOrderBy() {
		return orderBy != null ? orderBy : new HashMap<>();
	}
	
	public EzyObject toObject() {
		EzyObject obj = EzyEntityFactory.newObject();
		obj.put(OptionFields.SKIP, skip);
		obj.put(OptionFields.LIMIT, limit);
		return obj;
	}
}
