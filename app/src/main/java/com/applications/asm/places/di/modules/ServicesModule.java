package com.applications.asm.places.di.modules;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.applications.asm.data.entity.mapper.PlaceDetailsEntityMapper;
import com.applications.asm.data.entity.mapper.PlaceDetailsEntityMapperImpl;
import com.applications.asm.data.entity.mapper.PlaceEntityMapper;
import com.applications.asm.data.entity.mapper.PlaceEntityMapperImpl;
import com.applications.asm.data.entity.mapper.ReviewEntityMapper;
import com.applications.asm.data.entity.mapper.ReviewEntityMapperImpl;
import com.applications.asm.data.framework.PlaceService;
import com.applications.asm.data.framework.ServicePlacesDataSource;
import com.applications.asm.data.repository.PlacesRepositoryImpl;
import com.applications.asm.data.sources.PlacesDataSource;
import com.applications.asm.domain.repository.PlacesRepository;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ServicesModule {
    private final String apiKey;
    private final Application application;

    public ServicesModule(Application application, String apiKey) {
        this.application = application;
        this.apiKey = apiKey;
    }

    @Singleton
    @Provides
    PlacesRepository providePlacesRepository(
        PlacesDataSource placesDataSource,
        PlaceEntityMapper placeEntityMapper,
        PlaceDetailsEntityMapper placeDetailsEntityMapper,
        ReviewEntityMapper reviewEntityMapper
    ) {
        return new PlacesRepositoryImpl(placesDataSource, placeEntityMapper, placeDetailsEntityMapper, reviewEntityMapper);
    }

    @Provides
    PlacesDataSource providePlacesDataSource(PlaceService placeService, @Named("place_service_api_key") String apiKey) {
        return new ServicePlacesDataSource(placeService, apiKey);
    }

    @Provides
    PlaceEntityMapper providePlaceEntityMapper() {
        return new PlaceEntityMapperImpl();
    }

    @Provides
    PlaceDetailsEntityMapper providePlaceDetailsEntityMapper(Context context) {
        return new PlaceDetailsEntityMapperImpl(context);
    }

    @Provides
    ReviewEntityMapper provideReviewEntityMapper() {
        return new ReviewEntityMapperImpl();
    }

    @Provides
    PlaceService providePlaceService(@NonNull Retrofit retrofit) {
        return retrofit.create(PlaceService.class);
    }

    @Named("place_service_api_key")
    @Provides
    String provideApiKey() {
        return apiKey;
    }

    @Provides
    Context provideContext() {
        return application;
    }
}
