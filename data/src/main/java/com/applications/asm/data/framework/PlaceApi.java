package com.applications.asm.data.framework;

import com.applications.asm.data.model.PlaceDetailsModel;
import com.applications.asm.data.model.ResponseCategoriesModel;
import com.applications.asm.data.model.ResponsePlacesModel;
import com.applications.asm.data.model.ResponseReviewsModel;
import com.applications.asm.data.model.ResponseSuggestedPlacesModel;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PlaceApi {

    @GET("v3/businesses/search")
    Single<ResponsePlacesModel> getPlacesModel(@Header("Authorization") String apiKey, @Query("term") String place, @Query("latitude") double latitude, @Query("longitude") double longitude, @Query("radius") Integer radius, @Query("categories") String categories, @Query("offset") Integer initIndex, @Query("limit") Integer limit);

    @GET("v3/businesses/{id}")
    Single<PlaceDetailsModel> getPlaceDetailModel(@Header("Authorization") String apiKey, @Path("id") String placeId);

    @GET("v3/businesses/{id}/reviews")
    Single<ResponseReviewsModel> getReviewsModel(@Header("Authorization") String apiKey, @Path("id") String placeId);

    @GET("v3/autocomplete")
    Single<ResponseSuggestedPlacesModel> getSuggestedPlacesModel(@Header("Authorization") String apiKey, @Query("text") String word, @Query("latitude") double latitude, @Query("longitude") double longitude);

    @GET("v3/autocomplete")
    Single<ResponseCategoriesModel> getCategoriesModel(@Header("Authorization") String apiKey, @Query("text") String word, @Query("latitude") double latitude, @Query("longitude") double longitude, @Query("locale") String locale);
}
