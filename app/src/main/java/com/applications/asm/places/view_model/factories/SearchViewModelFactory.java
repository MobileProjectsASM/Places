package com.applications.asm.places.view_model.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.applications.asm.domain.use_cases.GetSuggestedPlacesUc;
import com.applications.asm.domain.use_cases.LoadLocationUc;
import com.applications.asm.places.model.mappers.LocationMapper;
import com.applications.asm.places.model.mappers.PlaceMapper;
import com.applications.asm.places.view_model.SearchViewModel;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class SearchViewModelFactory implements ViewModelProvider.Factory {
    private final GetSuggestedPlacesUc getSuggestedPlacesUc;
    private final LoadLocationUc loadLocationUc;
    private final PlaceMapper placeMapper;
    private final LocationMapper locationMapper;
    private final CompositeDisposable composite;

    public SearchViewModelFactory(
        GetSuggestedPlacesUc getSuggestedPlacesUc,
        LoadLocationUc loadLocationUc,
        PlaceMapper placeMapper,
        LocationMapper locationMapper,
        CompositeDisposable composite
    ) {
        this.getSuggestedPlacesUc = getSuggestedPlacesUc;
        this.loadLocationUc = loadLocationUc;
        this.placeMapper = placeMapper;
        this.locationMapper = locationMapper;
        this.composite = composite;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(SearchViewModel.class)) return (T) new SearchViewModel(getSuggestedPlacesUc, loadLocationUc, placeMapper, locationMapper, composite);
        throw new IllegalArgumentException("ViewModel class not found");
    }
}
