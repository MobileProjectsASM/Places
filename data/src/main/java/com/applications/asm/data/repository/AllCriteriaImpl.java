package com.applications.asm.data.repository;

import com.applications.asm.data.framework.local.database.ClientDb;
import com.applications.asm.data.framework.local.database.entities.CriterionEntity;
import com.applications.asm.data.mapper.CriterionMapper;
import com.applications.asm.domain.entities.Criterion;
import com.applications.asm.domain.repository.AllCriteria;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class AllCriteriaImpl implements AllCriteria {
    private final ClientDb clientDb;
    private final CriterionMapper criterionMapper;

    public AllCriteriaImpl(ClientDb clientDb, CriterionMapper criterionMapper) {
        this.clientDb = clientDb;
        this.criterionMapper = criterionMapper;
    }

    @Override
    public Single<List<Criterion>> thatAre(Criterion.Type type) {
        Single<List<CriterionEntity>> criteriaEntity;
        if(type == Criterion.Type.PRICE)
            criteriaEntity = clientDb.getCriterionDao().getCriteria(CriterionEntity.priceCriterion);
        else
            criteriaEntity = clientDb.getCriterionDao().getCriteria(CriterionEntity.sortCriterion);
        return criteriaEntity.map(criterionMapper::criteriaEntityToCriteria);
    }
}
