package com.applications.asm.data.mapper;

import com.applications.asm.domain.entities.Criterion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class CriterionMapperImpl implements CriterionMapper {

    @Inject
    public CriterionMapperImpl() {

    }

    @Override
    public List<Criterion> criteriaEntityToCriteria(Map<String, String> criteria) {
        List<Criterion> criteriaAux = new ArrayList<>();
        criteria.forEach((key, value) -> criteriaAux.add(new Criterion(key, value)));
        return criteriaAux;
    }
}
