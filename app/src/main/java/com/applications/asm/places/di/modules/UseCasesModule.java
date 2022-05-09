package com.applications.asm.places.di.modules;

import com.applications.asm.domain.executor.PostExecutionThread;
import com.applications.asm.domain.executor.ThreadExecutor;
import com.applications.asm.domain.repository.PlacesRepository;
import com.applications.asm.domain.use_cases.GetPlaceDetailsUc;
import com.applications.asm.domain.use_cases.GetPlacesUc;
import com.applications.asm.domain.use_cases.GetReviewsUc;
import com.applications.asm.domain.use_cases.GetSuggestedPlacesUc;

import dagger.Module;
import dagger.Provides;

@Module
public class UseCasesModule {

    @Provides
    GetSuggestedPlacesUc provideGetSuggestedPlacesUc(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, PlacesRepository placesRepository) {
        return new GetSuggestedPlacesUc(threadExecutor, postExecutionThread, placesRepository);
    }

    @Provides
    GetPlacesUc provideGetPlacesUc(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, PlacesRepository placesRepository) {
        return new GetPlacesUc(threadExecutor, postExecutionThread, placesRepository);
    }

    @Provides
    GetPlaceDetailsUc provideGetPlaceDetailsUc(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, PlacesRepository placesRepository) {
        return new GetPlaceDetailsUc(threadExecutor, postExecutionThread, placesRepository);
    }

    @Provides
    GetReviewsUc provideGetReviewsUc(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, PlacesRepository placesRepository) {
        return new GetReviewsUc(threadExecutor, postExecutionThread, placesRepository);
    }
}
