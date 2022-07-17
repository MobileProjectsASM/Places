package com.applications.asm.data.framework.local.database.daos;

import androidx.room.Dao;
import androidx.room.Query;

import com.applications.asm.data.framework.local.database.entities.CriterionEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface CriterionDao {
    @Query("SELECT * FROM criterion WHERE criterion_type = :criterionType")
    Single<List<CriterionEntity>> getCriteria(String criterionType);
}
