package com.tvd12.dahlia.core.factory;

import com.tvd12.dahlia.core.entity.Database;
import com.tvd12.dahlia.core.setting.DatabaseSetting;

import java.util.concurrent.atomic.AtomicInteger;

public class DatabaseFactory {

    protected final AtomicInteger maxDatabaseId;

    public DatabaseFactory(int currentMaxDatabaseId) {
        this.maxDatabaseId = new AtomicInteger(currentMaxDatabaseId);
    }

    public Database newDatabase(DatabaseSetting setting) {
        int databaseId = maxDatabaseId.incrementAndGet();
        setting.setDatabaseId(databaseId);
        return new Database(setting);
    }

    public Database createDatabase(DatabaseSetting setting) {
        return new Database(setting);
    }
}
