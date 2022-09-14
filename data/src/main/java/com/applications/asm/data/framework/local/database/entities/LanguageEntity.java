package com.applications.asm.data.framework.local.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "LANGUAGE")
public class LanguageEntity {
    @NonNull
    @ColumnInfo(name = "Id")
    @PrimaryKey
    private String id;

    @NonNull
    @ColumnInfo(name = "Name")
    private String name;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }
}
