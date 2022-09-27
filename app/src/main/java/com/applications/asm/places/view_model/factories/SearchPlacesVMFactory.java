package com.applications.asm.places.view_model.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.applications.asm.domain.use_cases.GetSuggestedPlacesUc;
import com.applications.asm.places.model.mappers.CoordinatesMapper;
import com.applications.asm.places.model.mappers.PlaceMapper;
import com.applications.asm.places.view_model.SearchPlacesViewModel;

import javax.inject.Inject;

public class SearchPlacesVMFactory implements ViewModelProvider.Factory {
    private final GetSuggestedPlacesUc getSuggestedPlacesUc;
    private final PlaceMapper placeMapper;
    private final CoordinatesMapper coordinatesMapper;

    @Inject
    public SearchPlacesVMFactory(GetSuggestedPlacesUc getSuggestedPlacesUc, PlaceMapper placeMapper, CoordinatesMapper coordinatesMapper) {
        this.getSuggestedPlacesUc = getSuggestedPlacesUc;
        this.placeMapper = placeMapper;
        this.coordinatesMapper = coordinatesMapper;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(SearchPlacesViewModel.class)) return (T) new SearchPlacesViewModel(getSuggestedPlacesUc, placeMapper, coordinatesMapper);
        throw new IllegalArgumentException("ViewModel class not found");
    }
}
