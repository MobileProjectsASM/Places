package com.applications.asm.places.view_model.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.applications.asm.domain.use_cases.GetCategoriesUc;
import com.applications.asm.places.model.mappers.CategoryMapper;
import com.applications.asm.places.view_model.CategoriesViewModel;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class CategoryViewModelFactory implements ViewModelProvider.Factory {
    private final GetCategoriesUc getCategoriesUc;
    private final CategoryMapper categoryMapper;
    private final CompositeDisposable composite;

    public CategoryViewModelFactory(
        GetCategoriesUc getCategoriesUc,
        CategoryMapper categoryMapper,
        CompositeDisposable composite
    ) {
        this.getCategoriesUc = getCategoriesUc;
        this.categoryMapper = categoryMapper;
        this.composite = composite;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(CategoriesViewModel.class)) return (T) new CategoriesViewModel(getCategoriesUc, categoryMapper, composite);
        throw new IllegalArgumentException("View Model class not found");
    }
}
