package com.applications.asm.places.di.modules;

import android.app.Application;
import android.content.Context;

import com.applications.asm.data.model.mapper.CategoryModelMapper;
import com.applications.asm.data.model.mapper.PlaceDetailsModelMapper;
import com.applications.asm.data.model.mapper.PlaceModelMapper;
import com.applications.asm.data.model.mapper.ReviewModelMapper;
import com.applications.asm.data.model.mapper.SuggestedPlaceModelMapper;
import com.applications.asm.data.repository.LocationRepositoryImpl;
import com.applications.asm.data.repository.PlacesRepositoryImpl;
import com.applications.asm.data.sources.LocationDataSourceSP;
import com.applications.asm.data.sources.PlacesDataSourceWS;
import com.applications.asm.domain.entities.Validators;
import com.applications.asm.domain.entities.ValidatorsImpl;
import com.applications.asm.domain.repository.LocationRepository;
import com.applications.asm.domain.repository.PlacesRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ServicesModule {

    private final Application application;

    public ServicesModule(Application application, String apiKey) {
        this.application = application;
    }

    @Singleton
    @Provides
    Validators provideValidators() {
        return new ValidatorsImpl();
    }

    @Singleton
    @Provides
    LocationRepository provideLocationRepository(LocationDataSourceSP locationDataSourceSP) {
        return new LocationRepositoryImpl(locationDataSourceSP);
    }

    @Singleton
    @Provides
    PlacesRepository providePlacesRepository(
        PlacesDataSourceWS placesDataSourceWs,
        PlaceModelMapper placeModelMapper,
        PlaceDetailsModelMapper placeDetailsModelMapper,
        ReviewModelMapper reviewModelMapper,
        SuggestedPlaceModelMapper suggestedPlaceModelMapper,
        CategoryModelMapper categoryModelMapper
    ) {
        return new PlacesRepositoryImpl(
            placesDataSourceWs,
            placeModelMapper,
            placeDetailsModelMapper,
            reviewModelMapper,
            suggestedPlaceModelMapper,
            categoryModelMapper
        );
    }

    @Provides
    Context provideContext() {
        return application;
    }
}
