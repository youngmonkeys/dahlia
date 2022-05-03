package com.tvd12.dahlia.core.command;

import com.tvd12.dahlia.core.entity.Databases;
import com.tvd12.dahlia.core.entity.DatabasesAware;
import com.tvd12.dahlia.core.factory.*;
import com.tvd12.dahlia.core.handler.*;
import com.tvd12.dahlia.core.query.QueryToPredicate;
import com.tvd12.dahlia.core.query.QueryToPredicateAware;
import com.tvd12.dahlia.core.setting.RecordSizeReader;
import com.tvd12.dahlia.core.setting.RecordSizeReaderAware;
import com.tvd12.dahlia.core.setting.RuntimeSetting;
import com.tvd12.dahlia.core.setting.RuntimeSettingAware;
import com.tvd12.dahlia.core.storage.Storage;
import com.tvd12.dahlia.core.storage.StorageAware;
import com.tvd12.ezyfox.builder.EzyBuilder;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"rawtypes", "unchecked"})
public class CommandExecutor {

    protected final Storage storage;
    protected final Databases databases;
    protected final RuntimeSetting runtimeSetting;
    protected final RecordSizeReader recordSizeReader;
    protected final QueryToPredicate queryToPredicate;
    protected final DatabaseFactory databaseFactory;
    protected final CollectionFactory collectionFactory;
    protected final DatabaseStorageFactory databaseStorageFactory;
    protected final CollectionStorageFactory collectionStorageFactory;
    protected final Map<CommandType, CommandHandler> handlers;

    protected CommandExecutor(Builder builder) {
        this.storage = builder.storage;
        this.databases = builder.databases;
        this.runtimeSetting = builder.runtimeSetting;
        this.recordSizeReader = builder.recordSizeReader;
        this.queryToPredicate = builder.queryToPredicate;
        this.databaseFactory = builder.databaseFactory;
        this.collectionFactory = builder.collectionFactory;
        this.databaseStorageFactory = builder.databaseStorageFactory;
        this.collectionStorageFactory = builder.collectionStorageFactory;
        this.handlers = newHandlers();
    }

    public static Builder builder() {
        return new Builder();
    }

    public <T> T execute(Command command) {
        CommandType type = command.getType();
        CommandHandler handler = handlers.get(type);
        T answer = (T) handler.handle(command);
        return answer;
    }

    protected Map<CommandType, CommandHandler> newHandlers() {
        Map<CommandType, CommandHandler> map = new HashMap<>();
        addHandler(map, CommandType.SAVE, new CommandSaveHandler());
        addHandler(map, CommandType.FIND, new CommandFindHandler());
        addHandler(map, CommandType.COUNT, new CommandCountHandler());
        addHandler(map, CommandType.INSERT, new CommandInsertHandler());
        addHandler(map, CommandType.UPDATE, new CommandUpdateHandler());
        addHandler(map, CommandType.DELETE, new CommandDeleteHandler());
        addHandler(map, CommandType.SAVE_ONE, new CommandSaveOneHandler());
        addHandler(map, CommandType.FIND_ONE, new CommandFindOneHandler());
        addHandler(map, CommandType.INSERT_ONE, new CommandInsertOneHandler());
        addHandler(map, CommandType.CREATE_DATABASE, new CommandCreateDatabaseHandler());
        addHandler(map, CommandType.CREATE_COLLECTION, new CommandCreateCollectionHandler());
        return map;
    }

    protected void addHandler(
        Map<CommandType, CommandHandler> map,
        CommandType type,
        CommandHandler handler) {
        if (handler instanceof StorageAware) {
            ((StorageAware) handler).setStorage(storage);
        }
        if (handler instanceof DatabasesAware) {
            ((DatabasesAware) handler).setDatabases(databases);
        }
        if (handler instanceof RuntimeSettingAware) {
            ((RuntimeSettingAware) handler).setRuntimeSetting(runtimeSetting);
        }
        if (handler instanceof RecordSizeReaderAware) {
            ((RecordSizeReaderAware) handler).setRecordSizeReader(recordSizeReader);
        }
        if (handler instanceof QueryToPredicateAware) {
            ((QueryToPredicateAware) handler).setQueryToPredicate(queryToPredicate);
        }
        if (handler instanceof DatabaseFactoryAware) {
            ((DatabaseFactoryAware) handler).setDatabaseFactory(databaseFactory);
        }
        if (handler instanceof CollectionFactoryAware) {
            ((CollectionFactoryAware) handler).setCollectionFactory(collectionFactory);
        }
        if (handler instanceof DatabaseStorageFactoryAware) {
            ((DatabaseStorageFactoryAware) handler).setDatabaseStorageFactory(databaseStorageFactory);
        }
        if (handler instanceof CollectionStorageFactoryAware) {
            ((CollectionStorageFactoryAware) handler).setCollectionStorageFactory(collectionStorageFactory);
        }
        map.put(type, handler);
    }

    public static class Builder implements EzyBuilder<CommandExecutor> {

        protected Storage storage;
        protected Databases databases;
        protected RuntimeSetting runtimeSetting;
        protected RecordSizeReader recordSizeReader;
        protected QueryToPredicate queryToPredicate;
        protected DatabaseFactory databaseFactory;
        protected CollectionFactory collectionFactory;
        protected DatabaseStorageFactory databaseStorageFactory;
        protected CollectionStorageFactory collectionStorageFactory;

        public Builder storage(Storage storage) {
            this.storage = storage;
            return this;
        }

        public Builder databases(Databases databases) {
            this.databases = databases;
            return this;
        }

        public Builder runtimeSetting(RuntimeSetting runtimeSetting) {
            this.runtimeSetting = runtimeSetting;
            return this;
        }

        public Builder recordSizeReader(RecordSizeReader recordSizeReader) {
            this.recordSizeReader = recordSizeReader;
            return this;
        }

        public Builder queryToPredicate(QueryToPredicate queryToPredicate) {
            this.queryToPredicate = queryToPredicate;
            return this;
        }

        public Builder databaseFatory(DatabaseFactory databaseFactory) {
            this.databaseFactory = databaseFactory;
            return this;
        }

        public Builder collectionFactory(CollectionFactory collectionFactory) {
            this.collectionFactory = collectionFactory;
            return this;
        }

        public Builder databaseStorageFactory(DatabaseStorageFactory databaseStorageFactory) {
            this.databaseStorageFactory = databaseStorageFactory;
            return this;
        }

        public Builder collectionStorageFactory(CollectionStorageFactory collectionStorageFactory) {
            this.collectionStorageFactory = collectionStorageFactory;
            return this;
        }

        @Override
        public CommandExecutor build() {
            return new CommandExecutor(this);
        }

    }

}
