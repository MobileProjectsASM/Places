package com.applications.asm.data.framework;

import android.util.Log;

import com.applications.asm.data.entity.PlaceDetailsEntity;
import com.applications.asm.data.entity.PlaceEntity;
import com.applications.asm.data.entity.ReviewEntity;
import com.applications.asm.data.sources.PlacesDataSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ServicePlacesDataSource implements PlacesDataSource {
    private static final String TAG = "ServicePlacesDataSource";
    private final PlaceService placeService;
    private final String apiKey;

    public ServicePlacesDataSource(PlaceService placeService, String apiKey) {
        this.placeService = placeService;
        this.apiKey = apiKey;
    }

    @Override
    public List<PlaceEntity> getPlacesEntity(String placeToFind, Double longitude, Double latitude, Integer radius, List<String> categories) throws IOException {
        Call<List<PlaceEntity>> placesEntity = placeService.getPlacesEntity(apiKey, placeToFind, longitude, latitude, radius, getCategories(categories));
        Response<List<PlaceEntity>> response = placesEntity.execute();
        if(response.isSuccessful())
            return response.body();
        Log.e(TAG, "Error " + response.code());
        return new ArrayList<>();
    }

    @Override
    public PlaceDetailsEntity getPlaceDetailsEntity(String placeId) throws IOException {
        Call<PlaceDetailsEntity> placeDetailsEntity = placeService.getPlaceDetailEntity(apiKey, placeId);
        Response<PlaceDetailsEntity> response = placeDetailsEntity.execute();
        if(response.isSuccessful())
            return response.body();
        Log.e(TAG, "Error " + response.code());
        return null;
    }

    @Override
    public List<ReviewEntity> getReviewsEntity(String placeId) throws IOException {
        Call<List<ReviewEntity>> reviewsEntity = placeService.getReviewsEntity(apiKey, placeId);
        Response<List<ReviewEntity>> response = reviewsEntity.execute();
        if(response.isSuccessful())
            return response.body();
        Log.e(TAG, "Error " + response.code());
        return new ArrayList<>();
    }

    private String getCategories(List<String> categories) {
        StringBuilder cat = new StringBuilder();
        for(int i = 0; i < categories.size(); i++) {
            if(i < categories.size() - 1)
                cat.append(categories.get(i)).append(",");
            else cat.append(categories.get(i));
        }
        return cat.toString();
    }
}
