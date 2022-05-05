package com.applications.asm.places.di.modules;

import com.applications.asm.data.framework.deserializer.SuggestedPlacesModelDeserializer;
import com.applications.asm.data.model.PlaceDetailsModel;
import com.applications.asm.data.model.ResponsePlacesModel;
import com.applications.asm.data.model.ResponseReviewsModel;
import com.applications.asm.data.model.ResponseSuggestedPlacesModel;
import com.applications.asm.data.framework.deserializer.PlaceDetailsModelDeserializer;
import com.applications.asm.data.framework.deserializer.PlacesModelDeserializer;
import com.applications.asm.data.framework.deserializer.ReviewsModelDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class DeserializerModule {

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
}
