package com.tvd12.dahlia.local;

import com.tvd12.dahlia.ICollection;
import com.tvd12.dahlia.core.command.CommandExecutor;
import com.tvd12.dahlia.core.command.CommandFind;
import com.tvd12.dahlia.core.command.CommandFindOne;
import com.tvd12.dahlia.core.command.CommandInsert;
import com.tvd12.dahlia.core.command.CommandInsertOne;
import com.tvd12.dahlia.core.command.CommandSave;
import com.tvd12.dahlia.core.command.CommandSaveOne;
import com.tvd12.dahlia.core.entity.Collection;
import com.tvd12.dahlia.query.DeleteOptions;
import com.tvd12.dahlia.query.FindOptions;
import com.tvd12.dahlia.query.UpdateOptions;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;

public class LocalCollection implements ICollection {

	protected final Collection store;
	protected final CommandExecutor commandExecutor;
	
	public LocalCollection(
			Collection store, CommandExecutor commandExecutor) {
		this.store = store;
		this.commandExecutor = commandExecutor;
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
	public EzyArray save(EzyArray records) {
		CommandSave command = new CommandSave(store.getId(), records);
		EzyArray result = commandExecutor.execute(command);
		return result;
	}

	@Override
	public EzyObject save(EzyObject record) {
		CommandSaveOne command = new CommandSaveOne(store.getId(), record);
		EzyObject result = commandExecutor.execute(command);
		return result;
	}

	@Override
	public EzyArray insert(EzyArray records) {
		CommandInsert command = new CommandInsert(store.getId(), records);
		EzyArray result = commandExecutor.execute(command);
		return result;
	}

	@Override
	public EzyObject insert(EzyObject record) {
		CommandInsertOne command = new CommandInsertOne(store.getId(), record);
		EzyObject result = commandExecutor.execute(command);
		return result;
	}

	@Override
	public EzyObject findOne(EzyObject query) {
		CommandFindOne command = new CommandFindOne(store.getId(), query);
		EzyObject result = commandExecutor.execute(command);
		return result;
	}
	
	@Override
	public EzyArray find(EzyObject query, FindOptions options) {
		CommandFind command = new CommandFind(store.getId(), query, options.toObject());
		EzyArray result = commandExecutor.execute(command);
		return result;
	}

	@Override
	public Object update(EzyObject query, EzyObject update, UpdateOptions options) {
		return null;
	}

	@Override
	public Object delete(EzyObject query, DeleteOptions options) {
		return null;
	}

	@Override
	public long count() {
		return 0;
	}
	
	@Override
	public long count(EzyObject query) {
		return 0;
	}
	
}
