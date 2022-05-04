package com.applications.asm.data.framework;

import android.util.Log;

import com.applications.asm.data.entity.PlaceDetailsEntity;
import com.applications.asm.data.entity.PlaceEntity;
import com.applications.asm.data.entity.ReviewEntity;
import com.applications.asm.data.sources.PlacesDataSource;
import com.applications.asm.domain.exception.ConnectionServer;

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
    public List<PlaceEntity> getPlacesEntity(String placeToFind, Double longitude, Double latitude, Integer radius, List<String> categories) throws ConnectionServer {
        Call<List<PlaceEntity>> placesEntity = placeService.getPlacesEntity(apiKey, placeToFind, longitude, latitude, radius, getCategories(categories));
        try {
            Response<List<PlaceEntity>> response = placesEntity.execute();
            if(response.isSuccessful())
                return response.body();
            else {
                Log.e(TAG, "Error " + response.code());
                return new ArrayList<>();
            }
        } catch (IOException e) {
            throw new ConnectionServer(e.getMessage());
        } catch (RuntimeException runtimeException) {
            Log.e(TAG, runtimeException.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public PlaceDetailsEntity getPlaceDetailsEntity(String placeId) throws ConnectionServer {
        Call<PlaceDetailsEntity> placeDetailsEntity = placeService.getPlaceDetailEntity(apiKey, placeId);
        try {
            Response<PlaceDetailsEntity> response = placeDetailsEntity.execute();
            if(response.isSuccessful())
                return response.body();
            else {
                Log.e(TAG, "Error " + response.code());
                return null;
            }
        } catch (IOException e) {
            throw new ConnectionServer(e.getMessage());
        } catch (RuntimeException runtimeException) {
            Log.e(TAG, runtimeException.getMessage());
            return null;
        }
    }

    @Override
    public List<ReviewEntity> getReviewsEntity(String placeId) throws ConnectionServer {
        Call<List<ReviewEntity>> reviewsEntity = placeService.getReviewsEntity(apiKey, placeId);
        try {
            Response<List<ReviewEntity>> response = reviewsEntity.execute();
            if(response.isSuccessful())
                return response.body();
            else {
                Log.e(TAG, "Error " + response.code());
                return new ArrayList<>();
            }
        } catch (IOException ioException) {
            throw new ConnectionServer(ioException.getMessage());
        } catch (RuntimeException runtimeException) {
            Log.e(TAG, runtimeException.getMessage());
            return new ArrayList<>();
        }
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
