package com.applications.asm.places.di.modules;

import com.applications.asm.domain.entities.Validators;
import com.applications.asm.domain.repository.LocationRepository;
import com.applications.asm.domain.repository.PlacesRepository;
import com.applications.asm.domain.use_cases.GetCategoriesUc;
import com.applications.asm.domain.use_cases.GetPlaceDetailsUc;
import com.applications.asm.domain.use_cases.GetPlacesUc;
import com.applications.asm.domain.use_cases.GetReviewsUc;
import com.applications.asm.domain.use_cases.GetSuggestedPlacesUc;
import com.applications.asm.domain.use_cases.LoadLocationUc;
import com.applications.asm.domain.use_cases.SaveLocationUc;
import com.applications.asm.domain.use_cases.ValidateFormLocationUc;
import com.applications.asm.domain.use_cases.ValidateFormSearchUc;
import com.applications.asm.domain.use_cases.base.UseCaseScheduler;

import dagger.Module;
import dagger.Provides;

@Module
public class UseCasesModule {
    @Provides
    ValidateFormLocationUc provideValidateFormLocationUc(
        UseCaseScheduler useCaseScheduler,
        Validators validators
    ) {
        return new ValidateFormLocationUc(
            useCaseScheduler,
            validators
        );
    }

    @Provides
    LoadLocationUc provideLoadLocationUc(
        UseCaseScheduler useCaseScheduler,
        LocationRepository locationRepository
    ) {
        return new LoadLocationUc(
            useCaseScheduler,
            locationRepository
        );
    }

    @Provides
    SaveLocationUc provideSaveLocationUc(
        UseCaseScheduler useCaseScheduler,
        LocationRepository locationRepository
    ) {
        return new SaveLocationUc(
            useCaseScheduler,
            locationRepository
        );
    }

    @Provides
    ValidateFormSearchUc provideValidateFormSearchUc(
        UseCaseScheduler useCaseScheduler,
        Validators validators
    ) {
        return new ValidateFormSearchUc(
            useCaseScheduler,
            validators
        );
    }

    @Provides
    GetSuggestedPlacesUc provideGetSuggestedPlacesUc(
        UseCaseScheduler useCaseScheduler,
        PlacesRepository placesRepository,
        Validators validators
    ) {
        return new GetSuggestedPlacesUc(
            useCaseScheduler,
            placesRepository,
            validators
        );
    }

    @Provides
    GetCategoriesUc provideGetCategoriesUc(
        UseCaseScheduler useCaseScheduler,
        PlacesRepository placesRepository
    ) {
        return new GetCategoriesUc(
            useCaseScheduler,
            placesRepository
        );
    }

    @Provides
    GetPlacesUc provideGetPlacesUc(
        UseCaseScheduler useCaseScheduler,
        PlacesRepository placesRepository,
        Validators validators
    ) {
        return new GetPlacesUc(
            useCaseScheduler,
            placesRepository,
            validators
        );
    }

    @Provides
    GetPlaceDetailsUc provideGetPlaceDetailsUc(
        UseCaseScheduler useCaseScheduler,
        PlacesRepository placesRepository
    ) {
        return new GetPlaceDetailsUc(
            useCaseScheduler,
            placesRepository
        );
    }

    @Provides
    GetReviewsUc provideGetReviewsUc(
        UseCaseScheduler useCaseScheduler,
        PlacesRepository placesRepository
    ) {
        return new GetReviewsUc(
            useCaseScheduler,
            placesRepository
        );
    }
}
