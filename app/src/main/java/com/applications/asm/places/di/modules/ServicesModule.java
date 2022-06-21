package com.applications.asm.places.di.modules;

import com.applications.asm.data.model.mapper.CategoryModelMapper;
import com.applications.asm.data.model.mapper.PlaceModelMapper;
import com.applications.asm.data.model.mapper.PriceModelMapper;
import com.applications.asm.data.model.mapper.ReviewModelMapper;
import com.applications.asm.data.model.mapper.SortCriteriaModelMapper;
import com.applications.asm.data.repository.CacheRepositoryImpl;
import com.applications.asm.data.repository.PlacesRepositoryImpl;
import com.applications.asm.data.sources.CacheDataSourceSP;
import com.applications.asm.data.sources.PlacesDataSourceWS;
import com.applications.asm.domain.entities.Validators;
import com.applications.asm.domain.entities.ValidatorsImpl;
import com.applications.asm.domain.repository.CacheRepository;
import com.applications.asm.domain.repository.PlacesRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ServicesModule {

    @Singleton
    @Provides
    Validators provideValidators() {
        return new ValidatorsImpl();
    }

    @Singleton
    @Provides
    CacheRepository provideCacheRepository(CacheDataSourceSP cacheDataSourceSP) {
        return new CacheRepositoryImpl(cacheDataSourceSP);
    }

    @Singleton
    @Provides
    PlacesRepository providePlacesRepository(
        PlacesDataSourceWS placesDataSourceWs,
        PlaceModelMapper placeModelMapper,
        ReviewModelMapper reviewModelMapper,
        CategoryModelMapper categoryModelMapper,
        SortCriteriaModelMapper sortCriteriaModelMapper,
        PriceModelMapper priceModelMapper
    ) {
        return new PlacesRepositoryImpl(
            placesDataSourceWs,
            placeModelMapper,
            reviewModelMapper,
            categoryModelMapper,
            sortCriteriaModelMapper,
            priceModelMapper
        );
    }
}
