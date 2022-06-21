package com.applications.asm.places.di.modules;

import android.content.Context;
import android.content.SharedPreferences;

import com.applications.asm.data.framework.AppSharedPreferences;
import com.applications.asm.data.framework.PlaceApi;
import com.applications.asm.data.framework.WebServicePlacesDataSource;
import com.applications.asm.data.sources.CacheDataSourceSP;
import com.applications.asm.data.sources.PlacesDataSourceWS;
import com.google.gson.Gson;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class DataSourcesModule {
    @Provides
    CacheDataSourceSP provideCacheDataSource(SharedPreferences sharedPreferences) {
        return new AppSharedPreferences(sharedPreferences);
    }

    @Provides
    PlacesDataSourceWS providePlacesDataSource(
        PlaceApi placeApi,
        @Named("place_service_api_key") String apiKey,
        @Named("gson_error") Gson gson,
        Context context
    ) {
        return new WebServicePlacesDataSource(
            placeApi,
            apiKey,
            gson,
            context
        );
    }
}
