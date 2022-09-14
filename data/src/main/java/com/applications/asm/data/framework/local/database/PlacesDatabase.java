package com.applications.asm.data.framework.local.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.applications.asm.data.framework.local.database.daos.CriterionDao;
import com.applications.asm.data.framework.local.database.daos.DayDao;
import com.applications.asm.data.framework.local.database.entities.AvailableIn;
import com.applications.asm.data.framework.local.database.entities.CriterionEntity;
import com.applications.asm.data.framework.local.database.entities.DayEntity;
import com.applications.asm.data.framework.local.database.entities.LanguageEntity;
import com.applications.asm.data.framework.local.database.entities.TextResourceEntity;

@Database(entities = {
    CriterionEntity.class,
    TextResourceEntity.class,
    DayEntity.class,
    AvailableIn.class,
    LanguageEntity.class
}, version = 1, exportSchema = false)
public abstract class PlacesDatabase extends RoomDatabase {
    public abstract CriterionDao getCriterionDao();
    public abstract DayDao getDayDao();
}
