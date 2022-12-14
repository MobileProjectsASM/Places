package com.applications.asm.data.framework.local.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "TEXT_RESOURCE")
public class TextResourceEntity {
    @NonNull
    @ColumnInfo(name = "Id")
    @PrimaryKey
    private String Id;

    @NonNull
    public String getId() {
        return Id;
    }

    public void setId(@NonNull String id) {
        Id = id;
    }
}
