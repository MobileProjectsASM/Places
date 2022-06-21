package com.applications.asm.data.model.mapper;

import com.applications.asm.data.model.SortCriteriaModel;
import com.applications.asm.domain.entities.SortCriteria;

import java.util.ArrayList;
import java.util.List;

public class SortCriteriaMapperImpl implements SortCriteriaMapper {
    @Override
    public SortCriteriaModel getSortCriteriaModel(SortCriteria sortCriteria) {
        return new SortCriteriaModel(sortCriteria.getId(), sortCriteria.getName());
    }

    @Override
    public SortCriteria getSortCriteria(SortCriteriaModel sortCriteriaModel) {
        return new SortCriteria(sortCriteriaModel.getId(), sortCriteriaModel.getName());
    }

    @Override
    public List<SortCriteria> getSortCriteriaList(List<SortCriteriaModel> sortCriteriaModelList) {
        List<SortCriteria> sortCriteriaList = new ArrayList<>();
        for(SortCriteriaModel sortCriteriaModel: sortCriteriaModelList)
            sortCriteriaList.add(getSortCriteria(sortCriteriaModel));
        return sortCriteriaList;
    }
}
