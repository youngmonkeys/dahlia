package com.tvd12.dahlia.local;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tvd12.dahlia.ICollection;
import com.tvd12.dahlia.IDatabase;
import com.tvd12.dahlia.core.command.CommandExecutor;
import com.tvd12.dahlia.core.entity.Collection;
import com.tvd12.dahlia.core.entity.Database;

public class LocalDatabase implements IDatabase {

	protected final Database store;
	protected final CommandExecutor commandExecutor;
	protected final Map<String, ICollection> collections;
	
	public LocalDatabase(
			Database store, CommandExecutor commandExecutor) {
		this.store = store;
		this.commandExecutor = commandExecutor;
		this.collections = new ConcurrentHashMap<>();
	}
	
	@Override
	public int getId() {
		return store.getId();
	}
	
	@Override
	public String getName() {
		return store.getName();
	}
	
	@Override
	public ICollection getCollection(String name) {
		ICollection collection = collections.computeIfAbsent(name, k -> {
			Collection collectionStore = store.getCollection(name);
			return new LocalCollection(collectionStore, commandExecutor);
		});
		return collection;
	}
	
}
