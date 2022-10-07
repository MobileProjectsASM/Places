package com.applications.asm.places.view_model.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.applications.asm.domain.use_cases.GetPlaceDetailsUc;
import com.applications.asm.places.model.mappers.PlaceMapper;
import com.applications.asm.places.view_model.PlaceDetailsViewModel;

public class PlaceDetailsVMFactory implements ViewModelProvider.Factory {
    private final GetPlaceDetailsUc getPlaceDetailsUc;
    private final PlaceMapper placeMapper;

    public PlaceDetailsVMFactory(GetPlaceDetailsUc getPlaceDetailsUc, PlaceMapper placeMapper) {
        this.getPlaceDetailsUc = getPlaceDetailsUc;
        this.placeMapper = placeMapper;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(PlaceDetailsViewModel.class)) return (T) new PlaceDetailsViewModel(getPlaceDetailsUc, placeMapper);
        throw new IllegalArgumentException("ViewModel class not found");
    }
}
