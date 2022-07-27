package com.applications.asm.data.repository;

import com.applications.asm.data.framework.local.database.PlacesDbClient;
import com.applications.asm.data.framework.local.database.entities.CriterionEntity;
import com.applications.asm.data.mapper.CriterionMapper;
import com.applications.asm.data.utils.ErrorUtils;
import com.applications.asm.domain.entities.Criterion;
import com.applications.asm.domain.repository.AllCriteria;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class AllCriteriaImpl implements AllCriteria {
    private final PlacesDbClient placesDbClient;
    private final CriterionMapper criterionMapper;

    @Inject
    public AllCriteriaImpl(PlacesDbClient placesDbClient, CriterionMapper criterionMapper) {
        this.placesDbClient = placesDbClient;
        this.criterionMapper = criterionMapper;
    }

    @Override
    public Single<List<Criterion>> thatAre(Criterion.Type type) {
        Single<Map<String, String>> criteriaSingle;
        if(type == Criterion.Type.PRICE)
            criteriaSingle = placesDbClient.getCriteria(CriterionEntity.priceCriterion, "es");
        else
            criteriaSingle = placesDbClient.getCriteria(CriterionEntity.sortCriterion, "es");
        return criteriaSingle.map(criterionMapper::criteriaEntityToCriteria)
                .onErrorResumeNext(throwable -> Single.error(ErrorUtils.resolveError(throwable, getClass())));
    }
}
