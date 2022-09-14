package com.applications.asm.data.framework.local.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "DAY", foreignKeys = @ForeignKey(
    entity = TextResourceEntity.class,
    parentColumns = "Id",
    childColumns = "TextResourceId"
))
public class DayEntity {
    @NonNull
    @ColumnInfo(name = "Id")
    @PrimaryKey
    private String id;

    @NonNull
    @ColumnInfo(name = "TextResourceId")
    private String textResourceId;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getTextResourceId() {
        return textResourceId;
    }

    public void setTextResourceId(@NonNull String textResourceId) {
        this.textResourceId = textResourceId;
    }
}
