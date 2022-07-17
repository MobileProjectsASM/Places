package com.applications.asm.data.model.mapper;

import com.applications.asm.data.model.SortCriteriaModel;

import java.util.List;

public interface SortCriteriaModelMapper {
    SortCriteriaModel getSortCriteriaModel(SortCriteria sortCriteria);
    SortCriteria getSortCriteria(SortCriteriaModel sortCriteriaModel);
    List<SortCriteria> getSortCriteriaList(List<SortCriteriaModel> sortCriteriaModelList);
}
