package com.applications.asm.places.di.modules;

import com.applications.asm.domain.entities.Validators;
import com.applications.asm.domain.repository.CacheRepository;
import com.applications.asm.domain.repository.PlacesRepository;
import com.applications.asm.domain.use_cases.GetCategoriesUc;
import com.applications.asm.domain.use_cases.GetPlaceDetailsUc;
import com.applications.asm.domain.use_cases.GetPlacesUc;
import com.applications.asm.domain.use_cases.GetPricesUc;
import com.applications.asm.domain.use_cases.GetReviewsUc;
import com.applications.asm.domain.use_cases.GetSortCriteriaUc;
import com.applications.asm.domain.use_cases.GetSuggestedPlacesUc;
import com.applications.asm.domain.use_cases.LoadLocationUc;
import com.applications.asm.domain.use_cases.SaveLocationUc;
import com.applications.asm.domain.use_cases.base.UseCaseScheduler;

import dagger.Module;
import dagger.Provides;

@Module
public class UseCasesModule {

    @Provides
    LoadLocationUc provideLoadLocationUc(
        UseCaseScheduler useCaseScheduler,
        CacheRepository cacheRepository
    ) {
        return new LoadLocationUc(
            useCaseScheduler,
            cacheRepository
        );
    }

    @Provides
    SaveLocationUc provideSaveLocationUc(
        UseCaseScheduler useCaseScheduler,
        CacheRepository cacheRepository
    ) {
        return new SaveLocationUc(
            useCaseScheduler,
            cacheRepository
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
    GetSortCriteriaUc provideGetSortCriteriaUc(
        UseCaseScheduler useCaseScheduler,
        PlacesRepository placesRepository
    ) {
        return new GetSortCriteriaUc(
            useCaseScheduler,
            placesRepository
        );
    }

    @Provides
    GetPricesUc provideGetPricesUc(
        UseCaseScheduler useCaseScheduler,
        PlacesRepository placesRepository
    ) {
        return new GetPricesUc(
            useCaseScheduler,
            placesRepository
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
