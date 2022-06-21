package com.applications.asm.places.view_model.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.applications.asm.domain.use_cases.GetPlacesUc;
import com.applications.asm.domain.use_cases.LoadLocationUc;
import com.applications.asm.places.model.mappers.CategoryMapper;
import com.applications.asm.places.model.mappers.LocationMapper;
import com.applications.asm.places.model.mappers.PlaceMapper;
import com.applications.asm.places.model.mappers.PriceMapper;
import com.applications.asm.places.model.mappers.SortCriteriaMapper;
import com.applications.asm.places.view_model.MainViewModel;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class MainViewModelFactory implements ViewModelProvider.Factory {
    private final LoadLocationUc loadLocationUc;
    private final GetPlacesUc getPlacesUc;
    private final PlaceMapper placeMapper;
    private final LocationMapper locationMapper;
    private final CategoryMapper categoryMapper;
    private final SortCriteriaMapper sortCriteriaMapper;
    private final PriceMapper priceMapper;
    private final CompositeDisposable composite;

    public MainViewModelFactory(
        LoadLocationUc loadLocationUc,
        GetPlacesUc getPlacesUc,
        PlaceMapper placeMapper,
        LocationMapper locationMapper,
        CategoryMapper categoryMapper,
        SortCriteriaMapper sortCriteriaMapper,
        PriceMapper priceMapper,
        CompositeDisposable composite
    ) {
        this.loadLocationUc = loadLocationUc;
        this.getPlacesUc = getPlacesUc;
        this.placeMapper = placeMapper;
        this.locationMapper = locationMapper;
        this.categoryMapper = categoryMapper;
        this.sortCriteriaMapper = sortCriteriaMapper;
        this.priceMapper = priceMapper;
        this.composite = composite;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(MainViewModel.class)) return (T) new MainViewModel(loadLocationUc, getPlacesUc, placeMapper, locationMapper, categoryMapper, sortCriteriaMapper, priceMapper, composite);
        throw new IllegalArgumentException("ViewModel class not found");
    }
}
