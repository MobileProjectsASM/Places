package com.applications.asm.places.view_model.factories;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.applications.asm.domain.use_cases.GetReviewsUc;
import com.applications.asm.places.model.mappers.ReviewMapper;
import com.applications.asm.places.view_model.ReviewsViewModel;

import javax.inject.Inject;

public class ReviewsVMFactory implements ViewModelProvider.Factory {
    private final GetReviewsUc getReviewsUc;
    private final ReviewMapper reviewMapper;

    @Inject
    public ReviewsVMFactory(GetReviewsUc getReviewsUc, ReviewMapper reviewMapper) {
        this.getReviewsUc = getReviewsUc;
        this.reviewMapper = reviewMapper;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ReviewsViewModel.class)) return (T) new ReviewsViewModel(getReviewsUc, reviewMapper);
        else throw new IllegalArgumentException("ViewModel class not found");
    }
}
