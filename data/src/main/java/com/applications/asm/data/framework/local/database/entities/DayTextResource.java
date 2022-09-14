package com.applications.asm.data.framework.local.database.entities;

import androidx.room.Relation;

public class DayTextResource {
    public TextResourceEntity textResourceEntity;

    @Relation(
        parentColumn = "Id",
        entityColumn = "TextResourceId"
    )
    public DayEntity dayEntity;
}
