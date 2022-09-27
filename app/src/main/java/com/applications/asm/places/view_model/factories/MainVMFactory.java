package com.applications.asm.places.view_model.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.applications.asm.domain.use_cases.GetCoordinatesUc;
import com.applications.asm.places.model.mappers.CoordinatesMapper;
import com.applications.asm.places.view_model.MainViewModel;

import javax.inject.Inject;

public class MainVMFactory implements ViewModelProvider.Factory {
    private final GetCoordinatesUc getCoordinatesUc;
    private final CoordinatesMapper coordinatesMapper;

    @Inject
    public MainVMFactory(GetCoordinatesUc getCoordinatesUc, CoordinatesMapper coordinatesMapper) {
        this.getCoordinatesUc = getCoordinatesUc;
        this.coordinatesMapper = coordinatesMapper;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(MainViewModel.class)) return (T) new MainViewModel(getCoordinatesUc, coordinatesMapper);
        throw new IllegalArgumentException("ViewModel class not found");
    }
}
