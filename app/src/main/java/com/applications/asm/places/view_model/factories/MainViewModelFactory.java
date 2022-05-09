package com.applications.asm.places.view_model.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.applications.asm.domain.use_cases.GetPlaceDetailsUc;
import com.applications.asm.domain.use_cases.GetPlacesUc;
import com.applications.asm.domain.use_cases.GetReviewsUc;
import com.applications.asm.places.model.mappers.PlaceDetailsMapper;
import com.applications.asm.places.model.mappers.PlaceMapper;
import com.applications.asm.places.model.mappers.ReviewMapper;
import com.applications.asm.places.view_model.MainViewModel;

public class MainViewModelFactory implements ViewModelProvider.Factory {
    private final GetPlacesUc getPlacesUc;
    private final GetPlaceDetailsUc getPlaceDetailsUc;
    private final GetReviewsUc getReviewsUc;
    private final PlaceMapper placeMapper;
    private final PlaceDetailsMapper placeDetailsMapper;
    private final ReviewMapper reviewMapper;

    public MainViewModelFactory(
        GetPlacesUc getPlacesUc,
        GetPlaceDetailsUc getPlaceDetailsUc,
        GetReviewsUc getReviewsUc,
        PlaceMapper placeMapper,
        PlaceDetailsMapper placeDetailsMapper,
        ReviewMapper reviewMapper
    ) {
        this.getPlacesUc = getPlacesUc;
        this.getPlaceDetailsUc = getPlaceDetailsUc;
        this.getReviewsUc = getReviewsUc;
        this.placeMapper = placeMapper;
        this.placeDetailsMapper = placeDetailsMapper;
        this.reviewMapper = reviewMapper;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(MainViewModel.class)) return (T) new MainViewModel(getPlacesUc, getPlaceDetailsUc, getReviewsUc, placeMapper, placeDetailsMapper, reviewMapper);
        throw new IllegalArgumentException("ViewModel class not found");
    }
}
