package com.applications.asm.places.view_model.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.applications.asm.domain.use_cases.SaveCoordinatesUc;
import com.applications.asm.places.model.mappers.CoordinatesMapper;
import com.applications.asm.places.view_model.CoordinatesViewModel;

import javax.inject.Inject;

public class CoordinatesVMFactory implements ViewModelProvider.Factory {
    private final SaveCoordinatesUc saveCoordinatesUc;
    private final CoordinatesMapper mapper;

    @Inject
    public CoordinatesVMFactory(SaveCoordinatesUc saveCoordinatesUc, CoordinatesMapper mapper) {
        this.saveCoordinatesUc = saveCoordinatesUc;
        this.mapper = mapper;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(CoordinatesViewModel.class)) return (T) new CoordinatesViewModel(saveCoordinatesUc, mapper);
        throw new IllegalArgumentException("ViewModel class not found");
    }
}
