package com.tvd12.dahlia.core.storage;

import com.tvd12.dahlia.core.codec.SettingCollectionDeserializer;
import com.tvd12.dahlia.core.codec.SettingCollectionSerializer;
import com.tvd12.dahlia.core.io.FileReaders;
import com.tvd12.dahlia.core.io.FileWriters;
import com.tvd12.dahlia.core.setting.CollectionSetting;
import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.codec.EzyObjectDeserializer;
import com.tvd12.ezyfox.codec.EzyObjectSerializer;

import java.nio.file.Path;
import java.nio.file.Paths;

import static com.tvd12.dahlia.core.constant.Constants.FILE_SETTINGS_DATA;

public class CollectionSettingStorage {

    protected final Path filePath;
    protected final SettingCollectionSerializer settingSerializer;
    protected final SettingCollectionDeserializer settingDeserializer;

    protected CollectionSettingStorage(Builder builder) {
        this.settingSerializer = new SettingCollectionSerializer(builder.objectSerializer);
        this.settingDeserializer = new SettingCollectionDeserializer(builder.objectDeserializer);
        this.filePath = Paths.get(builder.collectionDirectoryPath.toString(), FILE_SETTINGS_DATA);
    }

    public static Builder builder() {
        return new Builder();
    }

    public void store(CollectionSetting setting) {
        byte[] bytes = this.settingSerializer.serialize(setting);
        FileWriters.write(filePath, bytes);
    }

    public CollectionSetting read() {
        byte[] bytes = FileReaders.readBytes(filePath);
        return settingDeserializer.deserialize(bytes);
    }

    public static class Builder implements EzyBuilder<CollectionSettingStorage> {

        protected Path collectionDirectoryPath;
        protected EzyObjectSerializer objectSerializer;
        protected EzyObjectDeserializer objectDeserializer;

        public Builder collectionDirectoryPath(Path collectionDirectoryPath) {
            this.collectionDirectoryPath = collectionDirectoryPath;
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
        public CollectionSettingStorage build() {
            return new CollectionSettingStorage(this);
        }
    }
}