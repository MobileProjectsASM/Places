package com.applications.asm.places.model.mappers;

import com.applications.asm.domain.entities.SortCriteria;
import com.applications.asm.places.model.SortCriteriaVM;

import java.util.List;

public interface SortCriteriaMapper {
    SortCriteriaVM getSortCriteriaVM(SortCriteria sortCriteria);
    SortCriteria getSortCriteria(SortCriteriaVM sortCriteriaVM);
    List<SortCriteriaVM> getSortCriteriaVMList(List<SortCriteria> sortCriteriaList);
    List<SortCriteria> getSortCriteriaList(List<SortCriteriaVM> sortCriteriaVMList);
}
