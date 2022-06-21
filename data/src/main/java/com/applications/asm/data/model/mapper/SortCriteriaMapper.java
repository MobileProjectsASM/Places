package com.applications.asm.data.model.mapper;

import com.applications.asm.data.model.SortCriteriaModel;
import com.applications.asm.domain.entities.SortCriteria;

import java.util.List;

public interface SortCriteriaMapper {
    SortCriteriaModel getSortCriteriaModel(SortCriteria sortCriteria);
    SortCriteria getSortCriteria(SortCriteriaModel sortCriteriaModel);
    List<SortCriteria> getSortCriteriaList(List<SortCriteriaModel> sortCriteriaModelList);
}
