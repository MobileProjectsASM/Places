package com.applications.asm.data.framework;

import com.applications.asm.data.entity.PlaceDetailsEntity;
import com.applications.asm.data.entity.PlaceEntity;
import com.applications.asm.data.entity.ReviewEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PlaceService {

    @GET("v3/businesses/search")
    Call<List<PlaceEntity>> getPlacesEntity(@Header("Authorization") String apiKey, @Query("term") String place, @Query("latitude") double latitude, @Query("longitude") double longitude, @Query("radius") Integer radius, @Query("categories") String categories);

    @GET("v3/businesses/{id}")
    Call<PlaceDetailsEntity> getPlaceDetailEntity(@Header("Authorization") String apiKey, @Path("id") String placeId);

    @GET("v3/businesses/{id}/reviews")
    Call<List<ReviewEntity>> getReviewsEntity(@Header("Authorization") String apiKey, @Path("id") String placeId);
}
