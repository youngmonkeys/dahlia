package com.tvd12.dahlia.local;

import com.tvd12.dahlia.Dahlia;
import com.tvd12.dahlia.IDatabase;
import com.tvd12.dahlia.core.DahliaCore;
import com.tvd12.dahlia.core.DahliaCoreLoader;
import com.tvd12.dahlia.core.command.CommandCreateDatabase;
import com.tvd12.dahlia.core.command.CommandExecutor;
import com.tvd12.dahlia.core.entity.Database;
import com.tvd12.dahlia.core.setting.DatabaseSetting;
import com.tvd12.dahlia.exception.DatabaseNotFoundException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocalDahlia implements Dahlia {

    protected final DahliaCore store;
    protected final CommandExecutor commandExecutor;
    protected final Map<String, IDatabase> databases;

    public LocalDahlia(String dataDir) {
        this(loadStore(dataDir));
    }

    public LocalDahlia(DahliaCore store) {
        this.store = store;
        this.commandExecutor = store.getCommandExecutor();
        this.databases = new ConcurrentHashMap<>();
    }

    private static DahliaCore loadStore(String dataDir) {
        DahliaCoreLoader loader = new DahliaCoreLoader()
            .storageDirectory(dataDir);
        DahliaCore store = loader.load();
        return store;
    }

    @Override
    public IDatabase getDatabase(String name) {
        return databases.computeIfAbsent(name, k -> {
            Database databaseStore = store.getDatabase(name);
            if (databaseStore == null) {
                throw new DatabaseNotFoundException(name);
            }
            return new LocalDatabase(databaseStore, commandExecutor);
        });
    }

    @Override
    public IDatabase createDatabase(Object setting) {
        DatabaseSetting s = (DatabaseSetting) setting;
        CommandCreateDatabase command = new CommandCreateDatabase(s);
        Database databaseStore = commandExecutor.execute(command);
        LocalDatabase database = new LocalDatabase(databaseStore, commandExecutor);
        databases.put(databaseStore.getName(), database);
        return database;
    }

}
