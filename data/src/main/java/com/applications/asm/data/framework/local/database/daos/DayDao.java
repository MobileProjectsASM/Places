package com.applications.asm.data.framework.local.database.daos;

import androidx.room.Dao;
import androidx.room.MapInfo;
import androidx.room.Query;

import java.util.Map;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface DayDao {
    @MapInfo(keyColumn = "DAY_ID", valueColumn = "DAY_NAME")
    @Query("SELECT D.DAY_ID AS DAY_ID, TR.TEXT_DESCRIPTION AS DAY_NAME FROM Day D " +
            "INNER JOIN TextResource TR ON D.DAY_ID = TR.TEXT_DESCRIPTION" +
            " WHERE TR.TEXT_LANGUAGE = :language")
    Single<Map<String, String>> getDays(String language);
}
