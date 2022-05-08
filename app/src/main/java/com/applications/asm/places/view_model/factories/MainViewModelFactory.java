package com.applications.asm.places.view_model.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.applications.asm.domain.use_cases.GetPlacesUc;
import com.applications.asm.places.model.mappers.PlaceMVMapper;
import com.applications.asm.places.view_model.MainViewModel;

public class MainViewModelFactory implements ViewModelProvider.Factory {
    private final GetPlacesUc getPlacesUc;
    private final PlaceMVMapper placeMVMapper;

    public MainViewModelFactory(GetPlacesUc getPlacesUc, PlaceMVMapper placeMVMapper) {
        this.getPlacesUc = getPlacesUc;
        this.placeMVMapper = placeMVMapper;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(MainViewModel.class)) return (T) new MainViewModel(getPlacesUc, placeMVMapper);
        throw new IllegalArgumentException("ViewModel class not found");
    }
}
