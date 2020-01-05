package com.tvd12.dahlia.core.handler;

import com.tvd12.dahlia.core.command.CommandCount;
import com.tvd12.dahlia.core.entity.Collection;
import com.tvd12.dahlia.exception.CollectionNotFoundException;

public class CommandCountHandler extends CommandQueryHandler<CommandCount> {

	@Override
	public Object handle(CommandCount command) {
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
