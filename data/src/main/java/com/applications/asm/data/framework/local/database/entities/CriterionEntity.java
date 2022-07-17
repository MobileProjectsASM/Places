package com.applications.asm.data.framework.local.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "criterion")
public class CriterionEntity {
    @Ignore
    public static final String priceCriterion = "price_criterion";
    @Ignore
    public static final String sortCriterion = "sort_criterion";

    @PrimaryKey
    private String id;
    @ColumnInfo(name = "criterion_name")
    private String name;
    @ColumnInfo(name = "criterion_type")
    private String criterionType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCriterionType() {
        return criterionType;
    }

    public void setCriterionType(String criterionType) {
        this.criterionType = criterionType;
    }
}
