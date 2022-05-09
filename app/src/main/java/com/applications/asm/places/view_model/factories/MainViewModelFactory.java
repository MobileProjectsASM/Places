package com.applications.asm.places.view_model.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.applications.asm.domain.use_cases.GetPlaceDetailsUc;
import com.applications.asm.domain.use_cases.GetPlacesUc;
import com.applications.asm.places.model.mappers.PlaceDetailsMapper;
import com.applications.asm.places.model.mappers.PlaceMapper;
import com.applications.asm.places.view_model.MainViewModel;

public class MainViewModelFactory implements ViewModelProvider.Factory {
    private final GetPlacesUc getPlacesUc;
    private final GetPlaceDetailsUc getPlaceDetailsUc;
    private final PlaceMapper placeMapper;
    private final PlaceDetailsMapper placeDetailsMapper;

    public MainViewModelFactory(GetPlacesUc getPlacesUc, GetPlaceDetailsUc getPlaceDetailsUc, PlaceMapper placeMapper, PlaceDetailsMapper placeDetailsMapper) {
        this.getPlacesUc = getPlacesUc;
        this.getPlaceDetailsUc = getPlaceDetailsUc;
        this.placeMapper = placeMapper;
        this.placeDetailsMapper = placeDetailsMapper;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(MainViewModel.class)) return (T) new MainViewModel(getPlacesUc, getPlaceDetailsUc, placeMapper, placeDetailsMapper);
        throw new IllegalArgumentException("ViewModel class not found");
    }
}
