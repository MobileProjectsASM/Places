package com.applications.asm.places.view_model.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.applications.asm.domain.use_cases.GetCurrentLocationUc;
import com.applications.asm.domain.use_cases.LoadLocationUc;
import com.applications.asm.domain.use_cases.SaveLocationUc;
import com.applications.asm.places.model.mappers.LocationMapper;
import com.applications.asm.places.view_model.LocationViewModel;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class LocationViewModelFactory implements ViewModelProvider.Factory {
    private final LoadLocationUc loadLocationUc;
    private final SaveLocationUc saveLocationUc;
    private final GetCurrentLocationUc getCurrentLocationUc;
    private final LocationMapper locationMapper;
    private final CompositeDisposable composite;

    public LocationViewModelFactory(
        LoadLocationUc loadLocationUc,
        SaveLocationUc saveLocationUc,
        GetCurrentLocationUc getCurrentLocationUc,
        LocationMapper locationMapper,
        CompositeDisposable composite
    ) {
        this.loadLocationUc = loadLocationUc;
        this.saveLocationUc = saveLocationUc;
        this.getCurrentLocationUc = getCurrentLocationUc;
        this.locationMapper = locationMapper;
        this.composite = composite;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(LocationViewModel.class)) return (T) new LocationViewModel(loadLocationUc, saveLocationUc, getCurrentLocationUc, locationMapper, composite);
        throw new IllegalArgumentException("ViewModel class not found");
    }
}
