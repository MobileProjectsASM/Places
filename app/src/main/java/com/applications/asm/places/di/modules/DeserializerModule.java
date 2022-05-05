package com.applications.asm.places.di.modules;

import com.applications.asm.data.framework.deserializer.SuggestedPlacesModelDeserializer;
import com.applications.asm.data.model.PlaceDetailsModel;
import com.applications.asm.data.model.ResponsePlacesModel;
import com.applications.asm.data.model.ResponseReviewsModel;
import com.applications.asm.data.model.ResponseSuggestedPlacesModel;
import com.applications.asm.data.model.ReviewModel;
import com.applications.asm.data.framework.deserializer.PlaceDetailsModelDeserializer;
import com.applications.asm.data.framework.deserializer.PlacesModelDeserializer;
import com.applications.asm.data.framework.deserializer.ReviewsModelDeserializer;
import com.google.gson.JsonDeserializer;

import java.util.List;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class DeserializerModule {

    @Named("places_deserializer")
    @Provides
    JsonDeserializer<ResponsePlacesModel> providePlacesDeserializer() {
        return new PlacesModelDeserializer();
    }

    @Named("place_deserializer")
    @Provides
    JsonDeserializer<PlaceDetailsModel> providePlaceDetailsDeserializer() {
        return new PlaceDetailsModelDeserializer();
    }

    @Named("reviews_deserializer")
    @Provides
    JsonDeserializer<ResponseReviewsModel> provideReviewsDeserializer() {
        return new ReviewsModelDeserializer();
    }

    @Named("suggested_places_deserializer")
    @Provides
    JsonDeserializer<ResponseSuggestedPlacesModel> provideSuggestedPlacesDeserializer() {
        return new SuggestedPlacesModelDeserializer();
    }
}
