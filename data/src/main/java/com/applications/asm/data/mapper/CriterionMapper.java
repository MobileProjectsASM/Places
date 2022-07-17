package com.applications.asm.data.mapper;

import com.applications.asm.data.framework.local.database.entities.CriterionEntity;
import com.applications.asm.domain.entities.Criterion;

import java.util.List;

public interface CriterionMapper {
    List<Criterion> criteriaEntityToCriteria(List<CriterionEntity> criteria);
    Criterion criterionEntityToCriterion(CriterionEntity criterionEntity);
}
