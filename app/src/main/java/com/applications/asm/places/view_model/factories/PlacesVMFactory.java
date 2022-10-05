package com.applications.asm.places.view_model.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.applications.asm.domain.use_cases.GetPlacesUc;
import com.applications.asm.places.model.mappers.CategoryMapper;
import com.applications.asm.places.model.mappers.CoordinatesMapper;
import com.applications.asm.places.model.mappers.CriterionMapper;
import com.applications.asm.places.model.mappers.PlaceMapper;
import com.applications.asm.places.view_model.PlacesViewModel;

import javax.inject.Inject;

public class PlacesVMFactory implements ViewModelProvider.Factory {
    private final GetPlacesUc getPlacesUc;
    private final PlaceMapper placeMapper;
    private final CoordinatesMapper coordinatesMapper;
    private final CategoryMapper categoryMapper;
    private final CriterionMapper criterionMapper;

    @Inject
    public PlacesVMFactory(GetPlacesUc getPlacesUc, PlaceMapper placeMapper, CoordinatesMapper coordinatesMapper, CategoryMapper categoryMapper, CriterionMapper criterionMapper) {
        this.getPlacesUc = getPlacesUc;
        this.placeMapper = placeMapper;
        this.coordinatesMapper = coordinatesMapper;
        this.categoryMapper = categoryMapper;
        this.criterionMapper = criterionMapper;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(PlacesViewModel.class)) return (T) new PlacesViewModel(getPlacesUc, placeMapper, coordinatesMapper, categoryMapper, criterionMapper);
        else throw new IllegalArgumentException("ViewModel class not found");
    }
}
