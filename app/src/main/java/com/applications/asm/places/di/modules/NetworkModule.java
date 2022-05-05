package com.applications.asm.places.di.modules;

import com.applications.asm.data.model.PlaceDetailsModel;
import com.applications.asm.data.model.ResponsePlacesModel;
import com.applications.asm.data.model.ResponseReviewsModel;
import com.applications.asm.data.model.ResponseSuggestedPlacesModel;
import com.applications.asm.data.model.ReviewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
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
        @Named("places_deserializer") JsonDeserializer<ResponsePlacesModel> placesDeserializer,
        @Named("place_deserializer") JsonDeserializer<PlaceDetailsModel> placeDetailDeserializer,
        @Named("reviews_deserializer") JsonDeserializer<ResponseReviewsModel> reviewDeserializer,
        @Named("suggested_places_deserializer") JsonDeserializer<ResponseSuggestedPlacesModel> suggestedPlacesDeserializer
    ) {
        return new GsonBuilder()
                .registerTypeAdapter(ResponsePlacesModel.class, placesDeserializer)
                .registerTypeAdapter(PlaceDetailsModel.class, placeDetailDeserializer)
                .registerTypeAdapter(ResponseReviewsModel.class, reviewDeserializer)
                .registerTypeAdapter(ResponseSuggestedPlacesModel.class, suggestedPlacesDeserializer)
                .create();
    }
}
