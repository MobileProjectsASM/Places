package com.applications.asm.data.framework.local.database.daos;

import androidx.room.Dao;
import androidx.room.MapInfo;
import androidx.room.Query;

import java.util.Map;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface CriterionDao {
    @MapInfo(keyColumn = "CRITERION_ID", valueColumn = "CRITERION_NAME")
    @Query("SELECT C.CRITERION_ID AS CRITERION_ID, TR.TEXT_DESCRIPTION AS CRITERION_NAME FROM Criterion C INNER JOIN TextResource TR ON C.CRITERION_ID = TR.CRITERION_ID WHERE CRITERION_TYPE = :criterionType AND TR.TEXT_LANGUAGE = :language")
    Single<Map<String, String>> getCriteria(String criterionType, String language);
}
