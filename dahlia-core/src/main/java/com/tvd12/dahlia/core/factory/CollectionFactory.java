package com.tvd12.dahlia.core.factory;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.tvd12.dahlia.core.entity.Collection;
import com.tvd12.dahlia.core.entity.Index;
import com.tvd12.dahlia.core.setting.CollectionSetting;
import com.tvd12.dahlia.core.setting.IndexSetting;

public class CollectionFactory {

	protected final AtomicInteger maxCollectionId;
	
	public CollectionFactory(int currentMaxCollectionId) {
		this.maxCollectionId = new AtomicInteger(currentMaxCollectionId); 
	}
	
	public Collection newCollection(CollectionSetting setting) {
		int collectionId = maxCollectionId.incrementAndGet();
		setting.setCollectionId(collectionId);
		Collection collection = createCollection(setting);
		return collection;
	}
	
	public Collection createCollection(CollectionSetting setting) {
		Collection collection = new Collection(setting);
		Map<String, IndexSetting> indexes = setting.getIndexes();
		for(String indexName : indexes.keySet()) {
			IndexSetting indexSetting = indexes.get(indexName);
			Index index = new Index(indexSetting);
			collection.addIndex(index);
		}
		return collection;
	}
	
}
