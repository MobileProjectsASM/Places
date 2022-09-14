package com.applications.asm.data.framework.local.database.daos;

import androidx.room.Dao;
import androidx.room.MapInfo;
import androidx.room.Query;

import java.util.Map;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface CriterionDao {
    @MapInfo(keyColumn = "CriterionId", valueColumn = "Translation")
    @Query("SELECT C.ID AS CriterionId, AI.Translation AS Translation FROM Criterion C INNER JOIN TEXT_RESOURCE TR ON TR.Id = C.TextResourceId INNER JOIN AVAILABLE_IN AI ON AI.TextResourceId = TR.Id AND AI.LanguageId = :language WHERE C.Type = :criterionType")
    Single<Map<String, String>> getCriteria(String criterionType, String language);
}
