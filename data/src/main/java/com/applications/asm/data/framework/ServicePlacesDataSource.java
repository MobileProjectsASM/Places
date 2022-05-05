package com.applications.asm.data.framework;

import android.util.Log;

import com.applications.asm.data.model.PlaceDetailsModel;
import com.applications.asm.data.model.ResponsePlacesModel;
import com.applications.asm.data.model.ResponseReviewsModel;
import com.applications.asm.data.model.ReviewModel;
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
    public ResponsePlacesModel getPlacesModel(String placeToFind, Double longitude, Double latitude, Integer radius, List<String> categories) throws IOException {
        Call<ResponsePlacesModel> placesModel = placeService.getPlacesModel(apiKey, placeToFind, longitude, latitude, radius, getCategories(categories));
        Response<ResponsePlacesModel> response = placesModel.execute();
        if(response.isSuccessful())
            return response.body();
        Log.e(TAG, "Error " + response.code());
        return null;
    }

    @Override
    public PlaceDetailsModel getPlaceDetailsModel(String placeId) throws IOException {
        Call<PlaceDetailsModel> placeDetailsModel = placeService.getPlaceDetailModel(apiKey, placeId);
        Response<PlaceDetailsModel> response = placeDetailsModel.execute();
        if(response.isSuccessful())
            return response.body();
        Log.e(TAG, "Error " + response.code());
        return null;
    }

    @Override
    public ResponseReviewsModel getReviewsModel(String placeId) throws IOException {
        Call<ResponseReviewsModel> reviewsModel = placeService.getReviewsModel(apiKey, placeId);
        Response<ResponseReviewsModel> response = reviewsModel.execute();
        if(response.isSuccessful())
            return response.body();
        Log.e(TAG, "Error " + response.code());
        return null;
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
