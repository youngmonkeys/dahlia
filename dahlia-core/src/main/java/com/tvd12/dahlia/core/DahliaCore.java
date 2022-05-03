package com.tvd12.dahlia.core;

import com.tvd12.dahlia.core.command.Command;
import com.tvd12.dahlia.core.command.CommandExecutor;
import com.tvd12.dahlia.core.entity.Database;
import com.tvd12.dahlia.core.entity.Databases;
import com.tvd12.dahlia.core.factory.CollectionFactory;
import com.tvd12.dahlia.core.factory.CollectionStorageFactory;
import com.tvd12.dahlia.core.factory.DatabaseFactory;
import com.tvd12.dahlia.core.factory.DatabaseStorageFactory;
import com.tvd12.dahlia.core.query.QueryToPredicate;
import com.tvd12.dahlia.core.setting.RecordSizeReader;
import com.tvd12.dahlia.core.setting.RuntimeSetting;
import com.tvd12.dahlia.core.storage.Storage;
import com.tvd12.ezyfox.builder.EzyBuilder;
import lombok.Getter;

public class DahliaCore {

    protected final Storage storage;
    @Getter
    protected final Databases databases;
    protected final RuntimeSetting runtimeSetting;
    protected final RecordSizeReader recordSizeReader;
    protected final QueryToPredicate queryToPredicate;
    @Getter
    protected final CommandExecutor commandExecutor;
    protected final DatabaseFactory databaseFactory;
    protected final CollectionFactory collectionFactory;
    protected final DatabaseStorageFactory databaseStorageFactory;
    protected final CollectionStorageFactory collectionStorageFactory;

    public DahliaCore(Builder builder) {
        this.storage = builder.storage;
        this.databases = builder.databases;
        this.runtimeSetting = builder.runtimeSetting;
        this.databaseFactory = builder.databaseFactory;
        this.collectionFactory = builder.collectionFactory;
        this.databaseStorageFactory = builder.databaseStorageFactory;
        this.collectionStorageFactory = builder.collectionStorageFactory;
        this.recordSizeReader = new RecordSizeReader();
        this.queryToPredicate = new QueryToPredicate();
        this.commandExecutor = newCommandExecutor();
    }

    public static Builder builder() {
        return new Builder();
    }

    protected CommandExecutor newCommandExecutor() {
        return CommandExecutor.builder()
            .storage(storage)
            .databases(databases)
            .runtimeSetting(runtimeSetting)
            .recordSizeReader(recordSizeReader)
            .queryToPredicate(queryToPredicate)
            .databaseFatory(databaseFactory)
            .collectionFactory(collectionFactory)
            .databaseStorageFactory(databaseStorageFactory)
            .collectionStorageFactory(collectionStorageFactory)
            .build();
    }

    public <T> T execute(Command command) {
        return commandExecutor.execute(command);
    }

    public Database getDatabase(String name) {
        return databases.getDatabase(name);
    }

    public static class Builder implements EzyBuilder<DahliaCore> {

        protected Storage storage;
        protected Databases databases;
        protected RuntimeSetting runtimeSetting;
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

        public Builder databaseFactory(DatabaseFactory databaseFactory) {
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
        public DahliaCore build() {
            return new DahliaCore(this);
        }
    }
}
