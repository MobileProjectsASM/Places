package com.applications.asm.data.sources;

import com.applications.asm.data.model.PlaceDetailsModel;
import com.applications.asm.data.model.ResponseCategoriesModel;
import com.applications.asm.data.model.ResponsePlacesModel;
import com.applications.asm.data.model.ResponseReviewsModel;
import com.applications.asm.data.model.ResponseSuggestedPlacesModel;

import io.reactivex.rxjava3.core.Single;

public interface PlacesDataSourceWS {
    Single<ResponsePlacesModel> getPlacesModel(String placeToFind, Double longitude, Double latitude, Integer radius, String categories, Integer initIndex, Integer amount);
    Single<PlaceDetailsModel> getPlaceDetailsModel(String placeId);
    Single<ResponseReviewsModel> getReviewsModel(String placeId);
    Single<ResponseSuggestedPlacesModel> getSuggestedPlaces(String word, Double latitude, Double longitude);
    Single<ResponseCategoriesModel> getCategoriesModel(String word, Double latitude, Double longitude, String locale);
}
