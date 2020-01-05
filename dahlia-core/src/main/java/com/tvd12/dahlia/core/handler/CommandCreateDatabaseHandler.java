package com.tvd12.dahlia.core.handler;

import com.tvd12.dahlia.core.command.CommandCreateDatabase;
import com.tvd12.dahlia.core.entity.Database;
import com.tvd12.dahlia.core.factory.DatabaseFactory;
import com.tvd12.dahlia.core.factory.DatabaseFactoryAware;
import com.tvd12.dahlia.core.factory.DatabaseStorageFactory;
import com.tvd12.dahlia.core.factory.DatabaseStorageFactoryAware;
import com.tvd12.dahlia.core.setting.DatabaseSetting;
import com.tvd12.dahlia.core.setting.RuntimeSetting;
import com.tvd12.dahlia.core.setting.RuntimeSettingAware;
import com.tvd12.dahlia.core.storage.DatabaseStorage;
import com.tvd12.dahlia.exception.DatabaseExistedException;

import lombok.Setter;

public class CommandCreateDatabaseHandler 
		extends CommandAbstractHandler<CommandCreateDatabase>
		implements 
			RuntimeSettingAware,
			DatabaseFactoryAware,
			DatabaseStorageFactoryAware {

	@Setter
	protected RuntimeSetting runtimeSetting;
	@Setter
	protected DatabaseFactory databaseFactory;
	@Setter
	protected DatabaseStorageFactory databaseStorageFactory;
	
	@Override
	public Object handle(CommandCreateDatabase command) {
		DatabaseSetting setting = command.getSetting();
		String databaseName = setting.getDatabaseName();
		Database existedDatabase = databases.getDatabase(databaseName);
		if(existedDatabase != null)
			throw new DatabaseExistedException(databaseName);
		Database database = databaseFactory.newDatabase(setting);
		synchronized (runtimeSetting) {
			runtimeSetting.setMaxDatabaseId(database.getId());
			storage.storeRuntimeSetting(runtimeSetting);
		}
		databases.addDatabase(database);
		DatabaseStorage databaseStorage = 
				databaseStorageFactory.newDatabaseStorage(databaseName);
		storage.addDatabaseStorage(database.getId(), databaseStorage);
		databaseStorage.storeSetting(setting);
		return database;
	}
	
}
