package com.tvd12.dahlia.core.storage;

import com.tvd12.dahlia.core.codec.SettingDatabaseDeserializer;
import com.tvd12.dahlia.core.codec.SettingDatabaseSerializer;
import com.tvd12.dahlia.core.io.FileReaders;
import com.tvd12.dahlia.core.io.FileWriters;
import com.tvd12.dahlia.core.setting.DatabaseSetting;
import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.codec.EzyObjectDeserializer;
import com.tvd12.ezyfox.codec.EzyObjectSerializer;

import java.nio.file.Path;
import java.nio.file.Paths;

import static com.tvd12.dahlia.core.constant.Constants.FILE_SETTINGS_DATA;

public class DatabaseSettingStorage {

    protected final Path filePath;
    protected final SettingDatabaseSerializer settingSerializer;
    protected final SettingDatabaseDeserializer settingDeserializer;

    protected DatabaseSettingStorage(Builder builder) {
        this.settingSerializer = new SettingDatabaseSerializer(builder.objectSerializer);
        this.settingDeserializer = new SettingDatabaseDeserializer(builder.objectDeserializer);
        this.filePath = Paths.get(builder.databaseDirectoryPath.toString(), FILE_SETTINGS_DATA);
    }

    public static Builder builder() {
        return new Builder();
    }

    public void store(DatabaseSetting setting) {
        byte[] bytes = this.settingSerializer.serialize(setting);
        FileWriters.write(filePath, bytes);
    }

    public DatabaseSetting read() {
        byte[] bytes = FileReaders.readBytes(filePath);
        DatabaseSetting setting = settingDeserializer.deserialize(bytes);
        return setting;
    }

    public static class Builder implements EzyBuilder<DatabaseSettingStorage> {

        protected Path databaseDirectoryPath;
        protected EzyObjectSerializer objectSerializer;
        protected EzyObjectDeserializer objectDeserializer;

        public Builder databaseDirectoryPath(Path databaseDirectoryPath) {
            this.databaseDirectoryPath = databaseDirectoryPath;
            return this;
        }

        public Builder objectSerializer(EzyObjectSerializer objectSerializer) {
            this.objectSerializer = objectSerializer;
            return this;
        }

        public Builder objectDeserializer(EzyObjectDeserializer objectDeserializer) {
            this.objectDeserializer = objectDeserializer;
            return this;
        }

        @Override
        public DatabaseSettingStorage build() {
            return new DatabaseSettingStorage(this);
        }
    }
}