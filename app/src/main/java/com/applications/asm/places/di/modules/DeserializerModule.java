package com.applications.asm.places.di.modules;

import com.applications.asm.data.entity.PlaceDetailsEntity;
import com.applications.asm.data.entity.PlaceEntity;
import com.applications.asm.data.entity.ReviewEntity;
import com.applications.asm.data.framework.deserializer.PlaceDetailsEntityDeserializer;
import com.applications.asm.data.framework.deserializer.PlaceEntityDeserializer;
import com.applications.asm.data.framework.deserializer.ReviewsEntityDeserializer;
import com.google.gson.JsonDeserializer;

import java.util.List;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class DeserializerModule {

    @Named("places_deserializer")
    @Provides
    JsonDeserializer<List<PlaceEntity>> providePlacesDeserializer() {
        return new PlaceEntityDeserializer();
    }

    @Named("place_deserializer")
    @Provides
    JsonDeserializer<PlaceDetailsEntity> providePlaceDeserializer() {
        return new PlaceDetailsEntityDeserializer();
    }

    @Named("review_deserializer")
    @Provides
    JsonDeserializer<List<ReviewEntity>> provideReviewDeserializer() {
        return new ReviewsEntityDeserializer();
    }
}
