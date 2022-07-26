package com.applications.asm.data.mapper;

import com.applications.asm.domain.entities.Criterion;

import java.util.List;
import java.util.Map;

public interface CriterionMapper {
    List<Criterion> criteriaEntityToCriteria(Map<String, String> criteria);
}
