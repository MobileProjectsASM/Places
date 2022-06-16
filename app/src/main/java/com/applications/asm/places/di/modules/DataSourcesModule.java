package com.applications.asm.places.di.modules;

import android.content.SharedPreferences;

import com.applications.asm.data.framework.LocationSharedPreferences;
import com.applications.asm.data.framework.PlaceApi;
import com.applications.asm.data.framework.WebServicePlacesDataSource;
import com.applications.asm.data.sources.LocationDataSourceSP;
import com.applications.asm.data.sources.PlacesDataSourceWS;
import com.google.gson.Gson;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class DataSourcesModule {
    @Provides
    LocationDataSourceSP provideLocationDataSource(SharedPreferences sharedPreferences) {
        return new LocationSharedPreferences(sharedPreferences);
    }

    @Provides
    PlacesDataSourceWS providePlacesDataSource(
        PlaceApi placeApi,
        @Named("place_service_api_key") String apiKey,
        @Named("gson_error") Gson gson
    ) {
        return new WebServicePlacesDataSource(
            placeApi,
            apiKey,
            gson
        );
    }
}
