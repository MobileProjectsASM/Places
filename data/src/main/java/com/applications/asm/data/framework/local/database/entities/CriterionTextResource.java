package com.applications.asm.data.framework.local.database.entities;

import androidx.room.Relation;

public class CriterionTextResource {
    public TextResourceEntity textResourceEntity;
    @Relation(
        parentColumn = "Id",
        entityColumn = "TextResourceId"
    )
    public CriterionEntity criterionEntity;
}
