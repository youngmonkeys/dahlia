package com.tvd12.dahlia.core.handler;

import com.tvd12.dahlia.core.command.Count;
import com.tvd12.dahlia.core.entity.Collection;
import com.tvd12.dahlia.core.exception.CollectionNotFoundException;

public class CommandCountHandler extends CommandQueryHandler<Count> {

	@Override
	public Object handle(Count command) {
		int collectionId = command.getCollectionId();
		Collection collection = databases.getCollection(collectionId);
		if(collection == null)
			throw new CollectionNotFoundException(collectionId);
		
		synchronized (collection) {
			long count = collection.size();
			return count;
		}
	}
	
}
