package com.applications.asm.data.framework;

import com.applications.asm.data.model.PlaceDetailsModel;
import com.applications.asm.data.model.ResponsePlacesModel;
import com.applications.asm.data.model.ResponseReviewsModel;
import com.applications.asm.data.model.ResponseSuggestedPlacesModel;
import com.applications.asm.data.model.ReviewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PlaceService {

    @GET("v3/businesses/search")
    Call<ResponsePlacesModel> getPlacesModel(@Header("Authorization") String apiKey, @Query("term") String place, @Query("latitude") double latitude, @Query("longitude") double longitude, @Query("radius") Integer radius, @Query("categories") String categories);

    @GET("v3/businesses/{id}")
    Call<PlaceDetailsModel> getPlaceDetailModel(@Header("Authorization") String apiKey, @Path("id") String placeId);

    @GET("v3/businesses/{id}/reviews")
    Call<ResponseReviewsModel> getReviewsModel(@Header("Authorization") String apiKey, @Path("id") String placeId);

    @GET("v3/autocomplete")
    Call<ResponseSuggestedPlacesModel> getSuggestedPlacesModel(@Header("Authorization") String apiKey, @Query("text") String word, @Query("latitude") double latitude, @Query("longitude") double longitude);
}
