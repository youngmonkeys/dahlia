package com.tvd12.dahlia.core;

import com.tvd12.dahlia.core.command.CommandExecutor;
import com.tvd12.dahlia.core.entity.Databases;
import com.tvd12.dahlia.core.setting.Settings;
import com.tvd12.dahlia.core.storage.Storage;

public class DahliaCore {

	protected final Storage storage;
	protected final Settings settings;
	protected final Databases databases;
	protected final CommandExecutor commandExecutor;
	
	protected DahliaCore(Settings settings) {
		this.settings = settings;
		this.databases = new Databases();
		this.storage = newStorage();
		this.commandExecutor = newCommandExecutor();
	}
	
	protected Storage newStorage() {
		return new Storage(settings.getStorageDirectory());
	}
	
	protected CommandExecutor newCommandExecutor() {
		return new CommandExecutor(databases, storage);
	}
	
}
