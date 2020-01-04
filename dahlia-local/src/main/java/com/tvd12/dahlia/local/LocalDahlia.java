package com.tvd12.dahlia.local;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tvd12.dahlia.Dahlia;
import com.tvd12.dahlia.IDatabase;
import com.tvd12.dahlia.core.DahliaCore;
import com.tvd12.dahlia.core.command.CommandExecutor;
import com.tvd12.dahlia.core.entity.Database;

public class LocalDahlia implements Dahlia {

	protected final DahliaCore store;
	protected final CommandExecutor commandExecutor;
	protected final Map<String, IDatabase> databases;
	
	public LocalDahlia(DahliaCore store) {
		this.store = store;
		this.commandExecutor = store.getCommandExecutor();
		this.databases = new ConcurrentHashMap<>();
	}
	
	@Override
	public IDatabase getDatabase(String name) {
		IDatabase database = databases.computeIfAbsent(name, k -> {
			Database databaseStore = store.getDatabase(name);
			return new LocalDatabase(databaseStore, commandExecutor);
		});
		return database;
	}
	
}
