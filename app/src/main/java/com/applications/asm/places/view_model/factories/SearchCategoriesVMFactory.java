package com.applications.asm.places.view_model.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.applications.asm.domain.use_cases.GetCategoriesUc;
import com.applications.asm.places.model.mappers.CategoryMapper;
import com.applications.asm.places.model.mappers.CoordinatesMapper;
import com.applications.asm.places.view_model.SearchCategoriesViewModel;

import javax.inject.Inject;

public class SearchCategoriesVMFactory implements ViewModelProvider.Factory {
    private final GetCategoriesUc getCategoriesUc;
    private final CategoryMapper categoryMapper;
    private final CoordinatesMapper coordinatesMapper;

    @Inject
    public SearchCategoriesVMFactory(GetCategoriesUc getCategoriesUc, CategoryMapper categoryMapper, CoordinatesMapper coordinatesMapper) {
        this.getCategoriesUc = getCategoriesUc;
        this.categoryMapper = categoryMapper;
        this.coordinatesMapper = coordinatesMapper;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(SearchCategoriesViewModel.class)) return (T) new SearchCategoriesViewModel(getCategoriesUc, categoryMapper, coordinatesMapper);
        throw new IllegalArgumentException("ViewModel class not found");
    }
}
