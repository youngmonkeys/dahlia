package com.tvd12.dahlia;

import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;

public interface ICollection {

	int getId();
	
	String getName();
	
	EzyArray save(EzyArray records);
	
	EzyObject save(EzyObject record);
	
	EzyArray insert(EzyArray records);
	
	EzyObject insert(EzyObject record);
	
	EzyArray find(EzyObject query);
	
	EzyObject findOne(EzyObject query);

	Object update(EzyObject query, EzyObject update);
	
	Object delete(EzyObject query);
}
