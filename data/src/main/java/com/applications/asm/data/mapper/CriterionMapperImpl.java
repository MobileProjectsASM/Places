package com.applications.asm.data.mapper;

import com.applications.asm.data.framework.local.database.entities.CriterionEntity;
import com.applications.asm.domain.entities.Criterion;

import java.util.ArrayList;
import java.util.List;

public class CriterionMapperImpl implements CriterionMapper {

    @Override
    public List<Criterion> criteriaEntityToCriteria(List<CriterionEntity> criteriaEntity) {
        List<Criterion> criteria = new ArrayList<>();
        for(CriterionEntity criterionEntity: criteriaEntity)
            criteria.add(criterionEntityToCriterion(criterionEntity));
        return criteria;
    }

    @Override
    public Criterion criterionEntityToCriterion(CriterionEntity criterionEntity) {
        String id = criterionEntity.getId();
        String name = criterionEntity.getName();
        return new Criterion(id, name);
    }
}
