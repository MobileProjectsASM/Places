package com.applications.asm.places.model.mappers;

import com.applications.asm.domain.entities.Criterion;
import com.applications.asm.places.model.CriterionVM;

import java.util.List;

public interface CriterionMapper {
    Criterion.Type getType(CriterionVM.Type type);
    CriterionVM getCriterionVM(Criterion criterion);
    Criterion getCriterion(CriterionVM criterionVM);
    List<CriterionVM> getCriteriaVM(List<Criterion> criteria);
    List<Criterion> getCriteria(List<CriterionVM> criteriaVM);
}
