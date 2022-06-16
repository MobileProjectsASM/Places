package com.applications.asm.places.view_model.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.applications.asm.domain.use_cases.GetPlacesUc;
import com.applications.asm.places.model.mappers.PlaceMapper;
import com.applications.asm.places.view_model.MainViewModel;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class MainViewModelFactory implements ViewModelProvider.Factory {
    private final GetPlacesUc getPlacesUc;
    private final PlaceMapper placeMapper;
    private final CompositeDisposable composite;

    public MainViewModelFactory(
        GetPlacesUc getPlacesUc,
        PlaceMapper placeMapper,
        CompositeDisposable composite
    ) {
        this.getPlacesUc = getPlacesUc;
        this.placeMapper = placeMapper;
        this.composite = composite;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(MainViewModel.class)) return (T) new MainViewModel(getPlacesUc, placeMapper, composite);
        throw new IllegalArgumentException("ViewModel class not found");
    }
}
