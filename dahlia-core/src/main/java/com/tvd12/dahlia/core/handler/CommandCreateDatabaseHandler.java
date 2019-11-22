package com.tvd12.dahlia.core.handler;

import com.tvd12.dahlia.core.command.CreateDatabase;
import com.tvd12.dahlia.core.entity.Database;
import com.tvd12.dahlia.core.factory.DatabaseFactory;
import com.tvd12.dahlia.core.setting.DatabaseSetting;

public class CommandCreateDatabaseHandler 
		extends CommandAbstractHandler<CreateDatabase> {

	protected DatabaseFactory databaseFactory;
	
	@Override
	public Object handle(CreateDatabase command) {
		DatabaseSetting setting = command.getSetting();
		Database database = databaseFactory.newDatabase(setting);
		databases.addDatabase(database);
		storage.createDatabaseStorage(setting);
		return database;
	}
	
}
