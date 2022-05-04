package com.applications.asm.places.di.modules;

import com.applications.asm.data.entity.PlaceDetailsEntity;
import com.applications.asm.data.entity.PlaceEntity;
import com.applications.asm.data.entity.ReviewEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
    private final String placeServiceBaseUrl;

    public NetworkModule(String placeServiceBaseUrl) {
        this.placeServiceBaseUrl = placeServiceBaseUrl;
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(@Named("place_service_base_url") String baseUrl, GsonConverterFactory gsonConverterFactory) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(gsonConverterFactory)
                .build();
    }

    @Named("place_service_base_url")
    @Provides
    String provideBaseUrl() {
        return placeServiceBaseUrl;
    }

    @Provides
    GsonConverterFactory provideGsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    Gson provideGson(
        @Named("places_deserializer") JsonDeserializer<List<PlaceEntity>> placesDeserializer,
        @Named("place_deserializer") JsonDeserializer<PlaceDetailsEntity> placeDetailDeserializer,
        @Named("review_deserializer") JsonDeserializer<List<ReviewEntity>> reviewDeserializer
    ) {
        return new GsonBuilder()
                .registerTypeAdapter(PlaceEntity.class, placesDeserializer)
                .registerTypeAdapter(PlaceDetailsEntity.class, placeDetailDeserializer)
                .registerTypeAdapter(ReviewEntity.class, reviewDeserializer)
                .create();
    }
}
