package com.tvd12.dahlia.core.factory;

import java.util.concurrent.atomic.AtomicInteger;

import com.tvd12.dahlia.core.entity.Collection;
import com.tvd12.dahlia.core.setting.CollectionSetting;

public class CollectionFactory {

	protected final AtomicInteger maxCollectionId;
	
	public CollectionFactory(int currentMaxCollectionId) {
		this.maxCollectionId = new AtomicInteger(currentMaxCollectionId); 
	}
	
	public Collection newCollection(CollectionSetting setting) {
		int collectionId = maxCollectionId.incrementAndGet();
		setting.setCollectionId(collectionId);
		Collection collection = new Collection(setting);
		return collection;
	}
	
}
