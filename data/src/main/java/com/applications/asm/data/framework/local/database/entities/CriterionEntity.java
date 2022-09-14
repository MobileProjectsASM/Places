package com.applications.asm.data.framework.local.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "CRITERION", foreignKeys = @ForeignKey(
    entity = TextResourceEntity.class,
    parentColumns = "Id",
    childColumns = "TextResourceId"
))
public class CriterionEntity {
    @Ignore
    public static final String priceCriterion = "price_criterion";
    @Ignore
    public static final String sortCriterion = "sort_criterion";

    @NonNull
    @ColumnInfo(name = "Id")
    @PrimaryKey
    private String id;

    @NonNull
    @ColumnInfo(name = "Type")
    private String type;

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
    public String getType() {
        return type;
    }

    public void setType(@NonNull String type) {
        this.type = type;
    }

    @NonNull
    public String getTextResourceId() {
        return textResourceId;
    }

    public void setTextResourceId(@NonNull String textResourceId) {
        this.textResourceId = textResourceId;
    }
}
