package com.applications.asm.places.view_model.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.applications.asm.domain.use_cases.GetCategoriesUc;
import com.applications.asm.domain.use_cases.GetSuggestedPlacesUc;
import com.applications.asm.domain.use_cases.LoadLocationUc;
import com.applications.asm.domain.use_cases.ValidateFormSearchUc;
import com.applications.asm.places.model.mappers.CategoryMapper;
import com.applications.asm.places.model.mappers.LocationMapper;
import com.applications.asm.places.model.mappers.PlaceMapper;
import com.applications.asm.places.model.mappers.StatesMapper;
import com.applications.asm.places.view_model.SearchViewModel;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class SearchViewModelFactory implements ViewModelProvider.Factory {
    private final LoadLocationUc loadLocationUc;
    private final ValidateFormSearchUc validateFormSearchUc;
    private final GetSuggestedPlacesUc getSuggestedPlacesUc;
    private final GetCategoriesUc getCategoriesUc;
    private final LocationMapper locationMapper;
    private final StatesMapper statesMapper;
    private final PlaceMapper placeMapper;
    private final CategoryMapper categoryMapper;
    private final CompositeDisposable composite;

    public SearchViewModelFactory(
        LoadLocationUc loadLocationUc,
        ValidateFormSearchUc validateFormSearchUc,
        GetSuggestedPlacesUc getSuggestedPlacesUc,
        GetCategoriesUc getCategoriesUc,
        StatesMapper statesMapper,
        LocationMapper locationMapper,
        PlaceMapper placeMapper,
        CategoryMapper categoryMapper,
        CompositeDisposable composite
    ) {
        this.loadLocationUc = loadLocationUc;
        this.validateFormSearchUc = validateFormSearchUc;
        this.getSuggestedPlacesUc = getSuggestedPlacesUc;
        this.getCategoriesUc = getCategoriesUc;
        this.locationMapper = locationMapper;
        this.statesMapper = statesMapper;
        this.placeMapper = placeMapper;
        this.categoryMapper = categoryMapper;
        this.composite = composite;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(SearchViewModel.class)) return (T) new SearchViewModel(loadLocationUc, validateFormSearchUc, getSuggestedPlacesUc, getCategoriesUc, statesMapper, locationMapper, placeMapper, categoryMapper, composite);
        throw new IllegalArgumentException("ViewModel class not found");
    }
}
