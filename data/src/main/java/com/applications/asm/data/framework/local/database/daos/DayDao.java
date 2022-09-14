package com.applications.asm.data.framework.local.database.daos;

import androidx.room.Dao;
import androidx.room.MapInfo;
import androidx.room.Query;

import java.util.Map;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface DayDao {
    @MapInfo(keyColumn = "DayId", valueColumn = "Translation")
    @Query("SELECT D.Id AS DayId, AI.Translation AS Translation FROM Day D INNER JOIN TEXT_RESOURCE TR ON TR.Id = D.TextResourceId INNER JOIN AVAILABLE_IN AI ON AI.TextResourceId = TR.Id AND AI.LanguageId = :language")
    Single<Map<String, String>> getDays(String language);
}
