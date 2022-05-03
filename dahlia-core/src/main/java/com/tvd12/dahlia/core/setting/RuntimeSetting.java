package com.tvd12.dahlia.core.setting;

import lombok.Getter;

@Getter
public class RuntimeSetting {

    protected int maxDatabaseId;
    protected int maxCollectionId;

    public void setMaxDatabaseId(int maxDatabaseId) {
        if (maxDatabaseId > this.maxDatabaseId) {
            this.maxDatabaseId = maxDatabaseId;
        }
    }

    public void setMaxCollectionId(int maxCollectionId) {
        if (maxCollectionId > this.maxCollectionId) {
            this.maxCollectionId = maxCollectionId;
        }
    }
}
