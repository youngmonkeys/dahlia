package com.tvd12.dahlia.local;

import com.tvd12.dahlia.ICollection;
import com.tvd12.dahlia.core.command.CommandExecutor;
import com.tvd12.dahlia.core.command.Find;
import com.tvd12.dahlia.core.command.FindOne;
import com.tvd12.dahlia.core.command.Insert;
import com.tvd12.dahlia.core.command.InsertOne;
import com.tvd12.dahlia.core.entity.Collection;
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
		return null;
	}

	@Override
	public EzyObject save(EzyObject record) {
		return null;
	}

	@Override
	public EzyArray insert(EzyArray records) {
		Insert command = new Insert(store.getId(), records);
		EzyArray result = commandExecutor.execute(command);
		return result;
	}

	@Override
	public EzyObject insert(EzyObject record) {
		InsertOne command = new InsertOne(store.getId(), record);
		EzyObject result = commandExecutor.execute(command);
		return result;
	}

	@Override
	public EzyArray find(EzyObject query) {
		Find command = new Find(store.getId(), query);
		EzyArray result = commandExecutor.execute(command);
		return result;
	}

	@Override
	public EzyObject findOne(EzyObject query) {
		FindOne command = new FindOne(store.getId(), query);
		EzyObject result = commandExecutor.execute(command);
		return result;
	}

	@Override
	public Object update(EzyObject query, EzyObject update) {
		return null;
	}

	@Override
	public Object delete(EzyObject query) {
		return null;
	}

	
	
}
