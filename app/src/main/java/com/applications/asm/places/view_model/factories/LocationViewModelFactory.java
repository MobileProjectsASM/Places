package com.applications.asm.places.view_model.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.applications.asm.domain.use_cases.SaveLocationUc;
import com.applications.asm.domain.use_cases.ValidateFormLocationUc;
import com.applications.asm.places.model.mappers.LocationMapper;
import com.applications.asm.places.model.mappers.StatesMapper;
import com.applications.asm.places.view_model.LocationViewModel;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class LocationViewModelFactory implements ViewModelProvider.Factory {
    private final SaveLocationUc saveLocationUc;
    private final CompositeDisposable composite;

    public LocationViewModelFactory(
        SaveLocationUc saveLocationUc,
        CompositeDisposable composite
    ) {
        this.saveLocationUc = saveLocationUc;
        this.composite = composite;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(LocationViewModel.class)) return (T) new LocationViewModel(saveLocationUc, composite);
        throw new IllegalArgumentException("ViewModel class not found");
    }
}
