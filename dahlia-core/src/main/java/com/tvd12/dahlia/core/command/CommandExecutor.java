package com.tvd12.dahlia.core.command;

import java.util.HashMap;
import java.util.Map;

import com.tvd12.dahlia.core.constant.CommandType;
import com.tvd12.dahlia.core.entity.Databases;
import com.tvd12.dahlia.core.entity.DatabasesAware;
import com.tvd12.dahlia.core.handler.CommandHandler;
import com.tvd12.dahlia.core.handler.CommandInsertOneHandler;
import com.tvd12.dahlia.core.storage.Storage;
import com.tvd12.dahlia.core.storage.StorageAware;

public class CommandExecutor {

	protected final Storage storage;
	protected final Databases databases;
	protected final Map<CommandType, CommandHandler> handlers;
	
	public CommandExecutor(Databases databases, Storage storage) {
		this.storage = storage;
		this.databases = databases;
		this.handlers = newHandlers();
	}
	
	public <T> T execute(Command command) {
		CommandHandler handler = handlers.get(command);
		T answer = (T) handler.handle(command);
		return answer;
	}
	
	protected Map<CommandType, CommandHandler> newHandlers() {
		Map<CommandType, CommandHandler> map = new HashMap<>();
		addHandlers(map, CommandType.INSERT_ONE, new CommandInsertOneHandler());
		return map;
	}
	
	protected void addHandlers(
			Map<CommandType, CommandHandler> map, 
			CommandType type,
			CommandHandler handler) {
		if(handler instanceof StorageAware)
			((StorageAware)handler).setStorage(storage);
		if(handler instanceof DatabasesAware)
			((DatabasesAware)handler).setDatabases(databases);
	}
	
}
