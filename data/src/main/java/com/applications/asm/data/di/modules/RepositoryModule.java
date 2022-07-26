package com.applications.asm.data.di.modules;

import com.applications.asm.data.repository.AllCategoriesImpl;
import com.applications.asm.data.repository.AllCoordinatesImpl;
import com.applications.asm.data.repository.AllCriteriaImpl;
import com.applications.asm.data.repository.AllPlacesDetailsImpl;
import com.applications.asm.data.repository.AllPlacesImpl;
import com.applications.asm.data.repository.AllReviewsImpl;
import com.applications.asm.data.repository.AllSuggestedPlacesImpl;
import com.applications.asm.data.repository.ValidatorsImpl;
import com.applications.asm.domain.repository.Validators;
import com.applications.asm.domain.repository.AllCategories;
import com.applications.asm.domain.repository.AllCoordinates;
import com.applications.asm.domain.repository.AllCriteria;
import com.applications.asm.domain.repository.AllPlaces;
import com.applications.asm.domain.repository.AllPlacesDetails;
import com.applications.asm.domain.repository.AllReviews;
import com.applications.asm.domain.repository.AllSuggestedPlaces;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract Validators provideValidators(ValidatorsImpl validatorsImpl);

    @Singleton
    @Binds
    abstract AllPlaces providePlacesRepository(AllPlacesImpl allPlacesImpl);

    @Singleton
    @Binds
    abstract AllCoordinates provideAllCoordinates(AllCoordinatesImpl allCoordinatesImpl);

    @Singleton
    @Binds
    abstract AllSuggestedPlaces provideAllSuggestedPlaces(AllSuggestedPlacesImpl allSuggestedPlacesImpl);

    @Singleton
    @Binds
    abstract AllCriteria provideAllCriteria(AllCriteriaImpl allCriteriaImpl);

    @Singleton
    @Binds
    abstract AllCategories provideAllCategories(AllCategoriesImpl allCategoriesImpl);

    @Singleton
    @Binds
    abstract AllPlacesDetails provideAllPlacesDetails(AllPlacesDetailsImpl allPlacesDetailsImpl);

    @Singleton
    @Binds
    abstract AllReviews provideAllReviews(AllReviewsImpl allReviewsImpl);
}
