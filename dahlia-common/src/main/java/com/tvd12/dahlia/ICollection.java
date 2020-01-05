package com.tvd12.dahlia;

import com.tvd12.dahlia.query.FindOptions;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;

public interface ICollection {

	int getId();
	
	String getName();
	
	EzyArray save(EzyArray records);
	
	EzyObject save(EzyObject record);
	
	EzyArray insert(EzyArray records);
	
	EzyObject insert(EzyObject record);
	
	EzyObject findOne(EzyObject query);
	
	EzyArray find(EzyObject query, FindOptions options);

	Object update(EzyObject query, EzyObject update);
	
	Object delete(EzyObject query);
	
	long count();
	
	long count(EzyObject query);
}
