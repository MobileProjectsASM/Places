package com.applications.asm.data.framework.local.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "TextResource", primaryKeys = {"TEXT_ALIAS", "TEXT_LANGUAGE"})
public class TextResourceEntity {
    @NonNull
    @ColumnInfo(name = "TEXT_ALIAS")
    private String textAlias;
    @NonNull
    @ColumnInfo(name = "TEXT_LANGUAGE")
    private String textLanguage;
    @ColumnInfo(name = "TEXT_DESCRIPTION")
    private String textDescription;
    @ColumnInfo(name = "CRITERION_ID")
    private String criterionId;
    @ColumnInfo(name = "DAY_ID")
    private String dayId;

    public String getTextAlias() {
        return textAlias;
    }

    public void setTextAlias(String textAlias) {
        this.textAlias = textAlias;
    }

    public String getTextLanguage() {
        return textLanguage;
    }

    public void setTextLanguage(String textLanguage) {
        this.textLanguage = textLanguage;
    }

    public String getTextDescription() {
        return textDescription;
    }

    public void setTextDescription(String textDescription) {
        this.textDescription = textDescription;
    }

    public String getCriterionId() {
        return criterionId;
    }

    public void setCriterionId(String criterionId) {
        this.criterionId = criterionId;
    }

    public String getDayId() {
        return dayId;
    }

    public void setDayId(String dayId) {
        this.dayId = dayId;
    }
}
