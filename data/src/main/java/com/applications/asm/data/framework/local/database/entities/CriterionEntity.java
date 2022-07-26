package com.applications.asm.data.framework.local.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Criterion")
public class CriterionEntity {
    @Ignore
    public static final String priceCriterion = "price_criterion";
    @Ignore
    public static final String sortCriterion = "sort_criterion";

    @NonNull
    @ColumnInfo(name = "CRITERION_ID")
    @PrimaryKey
    private String criterionID;
    @ColumnInfo(name = "CRITERION_TYPE")
    private String criterionType;

    public String getCriterionID() {
        return criterionID;
    }

    public void setCriterionID(String criterionID) {
        this.criterionID = criterionID;
    }

    public String getCriterionType() {
        return criterionType;
    }

    public void setCriterionType(String criterionType) {
        this.criterionType = criterionType;
    }
}
