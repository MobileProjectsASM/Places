package com.applications.asm.places.view_model.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.applications.asm.domain.use_cases.GetSuggestedPlacesUc;
import com.applications.asm.places.model.mappers.PlaceNameMapper;
import com.applications.asm.places.view_model.SearchViewModel;

public class SearchViewModelFactory implements ViewModelProvider.Factory {
    private final GetSuggestedPlacesUc getSuggestedPlacesUc;
    private final PlaceNameMapper placeNameMapper;

    public SearchViewModelFactory(GetSuggestedPlacesUc getSuggestedPlacesUc, PlaceNameMapper placeNameMapper) {
        this.getSuggestedPlacesUc = getSuggestedPlacesUc;
        this.placeNameMapper = placeNameMapper;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(SearchViewModel.class)) return ((T) new SearchViewModel(getSuggestedPlacesUc, placeNameMapper));
        throw new IllegalArgumentException("ViewModel class not found");
    }
}
