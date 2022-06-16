package com.applications.asm.places.di.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.applications.asm.data.framework.PlaceApi;
import com.applications.asm.data.framework.deserializer.PlaceDetailsModelDeserializer;
import com.applications.asm.data.framework.deserializer.PlacesModelDeserializer;
import com.applications.asm.data.framework.deserializer.ReviewsModelDeserializer;
import com.applications.asm.data.framework.deserializer.SuggestedPlacesModelDeserializer;
import com.applications.asm.data.model.PlaceDetailsModel;
import com.applications.asm.data.model.ResponsePlacesModel;
import com.applications.asm.data.model.ResponseReviewsModel;
import com.applications.asm.data.model.ResponseSuggestedPlacesModel;
import com.applications.asm.data.model.mapper.CategoryModelMapper;
import com.applications.asm.data.model.mapper.CategoryModelMapperImpl;
import com.applications.asm.data.model.mapper.PlaceDetailsModelMapper;
import com.applications.asm.data.model.mapper.PlaceDetailsModelMapperImpl;
import com.applications.asm.data.model.mapper.PlaceModelMapper;
import com.applications.asm.data.model.mapper.PlaceModelMapperImpl;
import com.applications.asm.data.model.mapper.ReviewModelMapper;
import com.applications.asm.data.model.mapper.ReviewModelMapperImpl;
import com.applications.asm.data.model.mapper.SuggestedPlaceModelMapper;
import com.applications.asm.data.model.mapper.SuggestedPlaceModelMapperImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class UtilsModule {
    private final Application application;
    private final String namePreferences;
    private final String apiKey;
    private final String placeServiceBaseUrl;

    public UtilsModule(Application application, String namePreferences, String apiKey, String placeServiceBaseUrl) {
        this.application = application;
        this.namePreferences = namePreferences;
        this.apiKey = apiKey;
        this.placeServiceBaseUrl = placeServiceBaseUrl;
    }

    @Provides
    SharedPreferences provideSharedPreferences(Context context, @Named("name_preferences") String namePreferences) {
        return context.getSharedPreferences(namePreferences, Context.MODE_PRIVATE);
    }

    @Provides
    Context provideContext() {
        return application;
    }

    @Named("name_preferences")
    @Provides
    String provideNamePreferences() {
        return namePreferences;
    }

    @Provides
    PlaceApi providePlaceService(@NonNull Retrofit retrofit) {
        return retrofit.create(PlaceApi.class);
    }

    @Named("place_service_api_key")
    @Provides
    String provideApiKey() {
        return apiKey;
    }

    @Named("gson_error")
    @Provides
    Gson provideGsonError() {
        return new Gson();
    }

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
    GsonConverterFactory provideGsonConverterFactory(@Named("gson_deserializer") Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Named("gson_deserializer")
    @Provides
    Gson provideGsonDeserializer(
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

    @Named("places_deserializer")
    @Provides
    JsonDeserializer<ResponsePlacesModel> providePlacesDeserializer(@Named("gson_printer") Gson gson) {
        return new PlacesModelDeserializer(gson);
    }

    @Named("place_deserializer")
    @Provides
    JsonDeserializer<PlaceDetailsModel> providePlaceDetailsDeserializer(@Named("gson_printer") Gson gson) {
        return new PlaceDetailsModelDeserializer(gson);
    }

    @Named("reviews_deserializer")
    @Provides
    JsonDeserializer<ResponseReviewsModel> provideReviewsDeserializer(@Named("gson_printer") Gson gson) {
        return new ReviewsModelDeserializer(gson);
    }

    @Named("suggested_places_deserializer")
    @Provides
    JsonDeserializer<ResponseSuggestedPlacesModel> provideSuggestedPlacesDeserializer(@Named("gson_printer") Gson gson) {
        return new SuggestedPlacesModelDeserializer(gson);
    }

    @Named("gson_printer")
    @Provides
    Gson provideGsonPrinter() {
        return new GsonBuilder().setPrettyPrinting().create();
    }

    //Mappers
    @Provides
    PlaceModelMapper providePlaceModelMapper() {
        return new PlaceModelMapperImpl();
    }

    @Provides
    PlaceDetailsModelMapper providePlaceDetailsModelMapper(Context context) {
        return new PlaceDetailsModelMapperImpl(context);
    }

    @Provides
    ReviewModelMapper provideReviewModelMapper() {
        return new ReviewModelMapperImpl();
    }

    @Provides
    SuggestedPlaceModelMapper provideSuggestedPlaceModelMapper() {
        return new SuggestedPlaceModelMapperImpl();
    }

    @Provides
    CategoryModelMapper provideCategoryModelMapper() {
        return new CategoryModelMapperImpl();
    }
}
