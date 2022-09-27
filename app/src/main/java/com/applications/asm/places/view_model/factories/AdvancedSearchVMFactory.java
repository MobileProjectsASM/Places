package com.applications.asm.places.view_model.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.applications.asm.domain.use_cases.GetCriteriaUc;
import com.applications.asm.places.model.mappers.CriterionMapper;
import com.applications.asm.places.view_model.AdvancedSearchViewModel;

import javax.inject.Inject;

public class AdvancedSearchVMFactory implements ViewModelProvider.Factory {
    private final GetCriteriaUc getCriteriaUc;
    private final CriterionMapper criterionMapper;

    @Inject
    public AdvancedSearchVMFactory(GetCriteriaUc getCriteriaUc, CriterionMapper criterionMapper) {
        this.getCriteriaUc = getCriteriaUc;
        this.criterionMapper = criterionMapper;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(AdvancedSearchViewModel.class)) return (T) new AdvancedSearchViewModel(getCriteriaUc, criterionMapper);
        throw new IllegalArgumentException("ViewModel class not found");
    }
}
