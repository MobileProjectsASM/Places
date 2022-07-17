package com.applications.asm.data.framework.local.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.applications.asm.data.framework.local.database.daos.CriterionDao;
import com.applications.asm.data.framework.local.database.entities.CriterionEntity;

@Database(entities = {
    CriterionEntity.class
}, version = 1)
public abstract class ClientDb extends RoomDatabase {
    public abstract CriterionDao getCriterionDao();
}
