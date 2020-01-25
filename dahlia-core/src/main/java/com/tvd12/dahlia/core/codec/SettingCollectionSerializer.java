package com.tvd12.dahlia.core.codec;

import com.tvd12.dahlia.constant.SettingFields;
import com.tvd12.dahlia.core.setting.CollectionSetting;
import com.tvd12.ezyfox.codec.EzyObjectSerializer;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;

public class SettingCollectionSerializer implements SettingSerializer<CollectionSetting> {

	protected final SettingFieldToObjects fieldToObjects;
	protected final EzyObjectSerializer objectSerializer;
	
	public SettingCollectionSerializer(EzyObjectSerializer objectSerializer) {
		this.objectSerializer = objectSerializer;
		this.fieldToObjects = new SettingFieldToObjects();
	}
	
	@Override
	public byte[] serialize(CollectionSetting setting) {
		EzyObject object = collectionToObject(setting);
		byte[] bytes = objectSerializer.serialize(object);
		return bytes;
	}

	protected EzyObject collectionToObject(CollectionSetting setting) {
		return EzyEntityFactory.newObjectBuilder()
				.append(SettingFields.ID, setting.getCollectionId())
				.append(SettingFields.RECORD_SIZE, setting.getRecordSize())
				.append(SettingFields.FIELDS, fieldsToArray(setting))
				.build();
	}

	protected EzyArray fieldsToArray(CollectionSetting setting) {
		EzyArray answer = fieldToObjects.toArray(setting.getAllFields());
		return answer;
	}
}
