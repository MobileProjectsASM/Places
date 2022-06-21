package com.applications.asm.places.model.mappers;

import com.applications.asm.domain.entities.SortCriteria;
import com.applications.asm.places.model.SortCriteriaVM;

import java.util.ArrayList;
import java.util.List;

public class SortCriteriaMapperImpl implements SortCriteriaMapper {
    @Override
    public SortCriteriaVM getSortCriteriaVM(SortCriteria sortCriteria) {
        return new SortCriteriaVM(sortCriteria.getId(), sortCriteria.getName());
    }

    @Override
    public SortCriteria getSortCriteria(SortCriteriaVM sortCriteriaVM) {
        return new SortCriteria(sortCriteriaVM.getId(), sortCriteriaVM.getName());
    }

    @Override
    public List<SortCriteriaVM> getSortCriteriaVMList(List<SortCriteria> sortCriteriaList) {
        List<SortCriteriaVM> sortCriteriaVMList = new ArrayList<>();
        for(SortCriteria sortCriteria: sortCriteriaList)
            sortCriteriaVMList.add(getSortCriteriaVM(sortCriteria));
        return sortCriteriaVMList;
    }

    @Override
    public List<SortCriteria> getSortCriteriaList(List<SortCriteriaVM> sortCriteriaVMList) {
        List<SortCriteria> sortCriteriaList = new ArrayList<>();
        for(SortCriteriaVM sortCriteriaVM: sortCriteriaVMList)
            sortCriteriaList.add(getSortCriteria(sortCriteriaVM));
        return sortCriteriaList;
    }
}
