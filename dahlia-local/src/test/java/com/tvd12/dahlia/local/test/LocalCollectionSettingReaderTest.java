package com.tvd12.dahlia.local.test;

import com.tvd12.dahlia.core.setting.CollectionSetting;
import com.tvd12.dahlia.local.setting.LocalCollectionSettingReader;

public class LocalCollectionSettingReaderTest {

	public static void main(String[] args) {
		LocalCollectionSettingReader reader = new LocalCollectionSettingReader();
		CollectionSetting collectionSetting = reader.readFileSetting("mapping_example.json");
		System.out.println(collectionSetting.toMap());
	}
	
}
