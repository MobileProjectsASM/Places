package com.applications.asm.places.di.modules;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.applications.asm.data.model.mapper.PlaceDetailsModelMapper;
import com.applications.asm.data.model.mapper.PlaceDetailsModelMapperImpl;
import com.applications.asm.data.model.mapper.PlaceModelMapper;
import com.applications.asm.data.model.mapper.PlaceModelMapperImpl;
import com.applications.asm.data.model.mapper.ReviewModelMapper;
import com.applications.asm.data.model.mapper.ReviewModelMapperImpl;
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
        PlaceModelMapper placeModelMapper,
        PlaceDetailsModelMapper placeDetailsModelMapper,
        ReviewModelMapper reviewModelMapper
    ) {
        return new PlacesRepositoryImpl(placesDataSource, placeModelMapper, placeDetailsModelMapper, reviewModelMapper);
    }

    @Provides
    PlacesDataSource providePlacesDataSource(PlaceService placeService, @Named("place_service_api_key") String apiKey) {
        return new ServicePlacesDataSource(placeService, apiKey);
    }

    @Provides
    PlaceModelMapper providePlaceEntityMapper() {
        return new PlaceModelMapperImpl();
    }

    @Provides
    PlaceDetailsModelMapper providePlaceDetailsEntityMapper(Context context) {
        return new PlaceDetailsModelMapperImpl(context);
    }

    @Provides
    ReviewModelMapper provideReviewEntityMapper() {
        return new ReviewModelMapperImpl();
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
