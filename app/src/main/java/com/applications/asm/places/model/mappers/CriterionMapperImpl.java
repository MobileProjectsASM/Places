package com.applications.asm.places.model.mappers;

import com.applications.asm.domain.entities.Criterion;
import com.applications.asm.places.model.CriterionVM;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CriterionMapperImpl implements CriterionMapper {

    @Inject
    public CriterionMapperImpl() {

    }

    @Override
    public Criterion.Type getType(CriterionVM.Type type) {
        return type == CriterionVM.Type.SORT ? Criterion.Type.SORT : Criterion.Type.PRICE;
    }

    @Override
    public CriterionVM getCriterionVM(Criterion criterion) {
        return new CriterionVM(criterion.getId(), criterion.getName());
    }

    @Override
    public Criterion getCriterion(CriterionVM criterionVM) {
        return new Criterion(criterionVM.getId(), criterionVM.getName());
    }

    @Override
    public List<CriterionVM> getCriteriaVM(List<Criterion> criteria) {
        List<CriterionVM> criteriaVM = new ArrayList<>();
        for(Criterion criterion: criteria)
            criteriaVM.add(getCriterionVM(criterion));
        return criteriaVM;
    }

    @Override
    public List<Criterion> getCriteria(List<CriterionVM> criteriaVM) {
        List<Criterion> criteria = new ArrayList<>();
        for(CriterionVM criterionVM: criteriaVM)
            criteria.add(getCriterion(criterionVM));
        return criteria;
    }
}
