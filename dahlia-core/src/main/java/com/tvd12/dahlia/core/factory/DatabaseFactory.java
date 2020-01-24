package com.tvd12.dahlia.core.factory;

import java.util.concurrent.atomic.AtomicInteger;

import com.tvd12.dahlia.core.entity.Database;
import com.tvd12.dahlia.core.setting.DatabaseSetting;

public class DatabaseFactory {

	protected final AtomicInteger maxDatabaseId;
	
	public DatabaseFactory(int currentMaxDatabaseId) {
		this.maxDatabaseId = new AtomicInteger(currentMaxDatabaseId); 
	}
	
	public Database newDatabase(DatabaseSetting setting) {
		int databaseId = maxDatabaseId.incrementAndGet();
		setting.setDatabaseId(databaseId);
		Database database = new Database(setting);
		return database;
	}

	public Database createDatabase(DatabaseSetting setting) {
		Database database = new Database(setting);
		return database;
	}
	
}
