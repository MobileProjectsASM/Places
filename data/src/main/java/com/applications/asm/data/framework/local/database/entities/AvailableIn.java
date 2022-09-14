package com.applications.asm.data.framework.local.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "AVAILABLE_IN", primaryKeys = {"TextResourceId", "LanguageId"}, foreignKeys = {
    @ForeignKey(
        entity = TextResourceEntity.class,
        parentColumns = "Id",
        childColumns = "TextResourceId"
    ),
    @ForeignKey(
        entity = LanguageEntity.class,
        parentColumns = "Id",
        childColumns = "LanguageId"
    )
})
public class AvailableIn {
    @NonNull
    @ColumnInfo(name = "TextResourceId")
    private String textResourceId;

    @NonNull
    @ColumnInfo(name = "LanguageId")
    private String languageId;

    @NonNull
    @ColumnInfo(name = "Translation")
    private String translation;

    @NonNull
    public String getTextResourceId() {
        return textResourceId;
    }

    public void setTextResourceId(@NonNull String textResourceId) {
        this.textResourceId = textResourceId;
    }

    @NonNull
    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(@NonNull String languageId) {
        this.languageId = languageId;
    }

    @NonNull
    public String getTranslation() {
        return translation;
    }

    public void setTranslation(@NonNull String translation) {
        this.translation = translation;
    }
}
