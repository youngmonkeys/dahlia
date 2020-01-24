package com.tvd12.dahlia.core.constant;

public final class Constants {

	public static final String FIELD_ID = "_id";
	
	public static final int MAX_UTF8_CHAR_BYTES = 4;
	public static final int MAX_STRING_SIZE_BYTES = 4;
	public static final int MAX_UUID_SIZE = 36;
	public static final int MAX_BIGDECIMAL_SIZE = 32;
	
	public static final int RECORD_HEADER_SIZE = 1;
	
	public static final String MODE_READ_WRITE = "rw";

	public static final String SETTING_FIELD_ID = "id";
	public static final String SETTING_FIELD_NAME = "name";
	public static final String SETTING_FIELD_TYPE = "type";
	public static final String SETTING_FIELD_ITEM = "item";
	public static final String SETTING_FIELD_NULLABLE = "nullable";
	public static final String SETTING_FIELD_FIELDS = "fields";
	public static final String SETTING_FIELD_INDEXES = "indexes";
	public static final String SETTING_FIELD_DEFAULT = "default";
	public static final String SETTING_FIELD_MAX_SIZE = "max_size";
	public static final String SETTING_FIELD_RECORD_SIZE = "record_size";
	public static final String SETTING_FIELD_MAX_DATABASE_ID = "database_max_id";
	public static final String SETTING_FIELD_MAX_COLLECTION_ID = "collection_max_id";
	
	public static final String RESULT_FIELD_EXISTED = "existed";
	
	public static final String DIRECTORY_DATABASES = "databases";
	
	public static final String FILE_RUNTIME_DATA = "runtime.dat";
	public static final String FILE_SETTINGS_DATA = "settings.dat"; 
	public static final String FILE_RECORDS_DATA = "records.dat";
	
	private Constants() {
	}

}
