package com.applications.asm.data.framework;

import android.util.Log;

import com.applications.asm.data.exception.PlacesDataSourceWSError;
import com.applications.asm.data.exception.PlacesDataSourceWSException;
import com.applications.asm.data.model.ErrorResponse;
import com.applications.asm.data.model.PlaceDetailsModel;
import com.applications.asm.data.model.ResponseCategoriesModel;
import com.applications.asm.data.model.ResponsePlacesModel;
import com.applications.asm.data.model.ResponseReviewsModel;
import com.applications.asm.data.model.ResponseSuggestedPlacesModel;
import com.applications.asm.data.sources.PlacesDataSourceWS;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class WebServicePlacesDataSource implements PlacesDataSourceWS {
    private static final String TAG = "ServicePlacesDataSource";
    private final PlaceService placeService;
    private final String apiKey;
    private final Gson gsonError;

    public WebServicePlacesDataSource(PlaceService placeService, String apiKey, Gson gsonError) {
        this.placeService = placeService;
        this.apiKey = apiKey;
        this.gsonError = gsonError;
    }

    @Override
    public ResponsePlacesModel getPlacesModel(String placeToFind, Double longitude, Double latitude, Integer radius, String categories, Integer initIndex, Integer amount) throws IOException, RuntimeException, PlacesDataSourceWSException {
        Call<ResponsePlacesModel> placesModel = placeService.getPlacesModel(apiKey, placeToFind, latitude, longitude, radius, categories, initIndex, amount);
        Response<ResponsePlacesModel> response = placesModel.execute();
        if(response.isSuccessful()) return response.body();
        else {
            String error = "There is no description of the error";
            ResponseBody responseBody = response.errorBody();
            if(responseBody != null) {
                ErrorResponse e = gsonError.fromJson(responseBody.string(), ErrorResponse.class);
                error = e.getError().getCode() + ": " + e.getError().getDescription();
                error += e.getError().getField() != null ? "The field " + e.getError().getField() + " has value " + e.getError().getInstance() : "";
            }
            Log.e(TAG, error);
            int code = response.code();
            if(code >= 300 && code < 400) throw new PlacesDataSourceWSException(PlacesDataSourceWSError.REDIRECTIONS);
            else if(code >= 400 && code < 500) throw new PlacesDataSourceWSException(PlacesDataSourceWSError.CLIENT_ERROR);
            else throw new PlacesDataSourceWSException(PlacesDataSourceWSError.SERVER_ERROR);
        }
    }

    @Override
    public PlaceDetailsModel getPlaceDetailsModel(String placeId) throws IOException, RuntimeException, PlacesDataSourceWSException {
        Call<PlaceDetailsModel> placeDetailsModel = placeService.getPlaceDetailModel(apiKey, placeId);
        Response<PlaceDetailsModel> response = placeDetailsModel.execute();
        if(response.isSuccessful()) return response.body();
        else {
            String error = "There is no description of the error";
            ResponseBody responseBody = response.errorBody();
            if(responseBody != null) {
                ErrorResponse e = gsonError.fromJson(responseBody.string(), ErrorResponse.class);
                error = e.getError().getCode() + ": " + e.getError().getDescription();
                error += e.getError().getField() != null ? "The field " + e.getError().getField() + " has value " + e.getError().getInstance() : "";
            }
            Log.e(TAG, error);
            int code = response.code();
            if(code >= 300 && code < 400) throw new PlacesDataSourceWSException(PlacesDataSourceWSError.REDIRECTIONS);
            else if(code >= 400 && code < 500) throw new PlacesDataSourceWSException(PlacesDataSourceWSError.CLIENT_ERROR);
            else throw new PlacesDataSourceWSException(PlacesDataSourceWSError.SERVER_ERROR);
        }
    }

    @Override
    public ResponseReviewsModel getReviewsModel(String placeId) throws IOException, RuntimeException, PlacesDataSourceWSException {
        Call<ResponseReviewsModel> reviewsModel = placeService.getReviewsModel(apiKey, placeId);
        Response<ResponseReviewsModel> response = reviewsModel.execute();
        if(response.isSuccessful()) return response.body();
        else {
            String error = "There is no description of the error";
            ResponseBody responseBody = response.errorBody();
            if(responseBody != null) {
                ErrorResponse e = gsonError.fromJson(responseBody.string(), ErrorResponse.class);
                error = e.getError().getCode() + ": " + e.getError().getDescription();
                error += e.getError().getField() != null ? "The field " + e.getError().getField() + " has value " + e.getError().getInstance() : "";
            }
            Log.e(TAG, error);
            int code = response.code();
            if(code >= 300 && code < 400) throw new PlacesDataSourceWSException(PlacesDataSourceWSError.REDIRECTIONS);
            else if(code >= 400 && code < 500) throw new PlacesDataSourceWSException(PlacesDataSourceWSError.CLIENT_ERROR);
            else throw new PlacesDataSourceWSException(PlacesDataSourceWSError.SERVER_ERROR);
        }
    }

    @Override
    public ResponseSuggestedPlacesModel getSuggestedPlaces(String word, Double latitude, Double longitude) throws IOException, RuntimeException, PlacesDataSourceWSException {
        Call<ResponseSuggestedPlacesModel> suggestedPlacesModel = placeService.getSuggestedPlacesModel(apiKey, word, latitude, longitude);
        Response<ResponseSuggestedPlacesModel> response = suggestedPlacesModel.execute();
        if(response.isSuccessful()) return response.body();
        else {
            String error = "There is no description of the error";
            ResponseBody responseBody = response.errorBody();
            if(responseBody != null) {
                ErrorResponse e = gsonError.fromJson(responseBody.string(), ErrorResponse.class);
                error = e.getError().getCode() + ": " + e.getError().getDescription();
                error += e.getError().getField() != null ? "The field " + e.getError().getField() + " has value " + e.getError().getInstance() : "";
            }
            Log.e(TAG, error);
            int code = response.code();
            if(code >= 300 && code < 400) throw new PlacesDataSourceWSException(PlacesDataSourceWSError.REDIRECTIONS);
            else if(code >= 400 && code < 500) throw new PlacesDataSourceWSException(PlacesDataSourceWSError.CLIENT_ERROR);
            else throw new PlacesDataSourceWSException(PlacesDataSourceWSError.SERVER_ERROR);
        }
    }

    @Override
    public ResponseCategoriesModel getCategoriesModel(String word, Double latitude, Double longitude, String locale) throws IOException, RuntimeException, PlacesDataSourceWSException {
        Call<ResponseCategoriesModel> categoriesModel = placeService.getCategoriesModel(apiKey, word, latitude, longitude, locale);
        Response<ResponseCategoriesModel> response = categoriesModel.execute();
        if(response.isSuccessful()) return response.body();
        else {
            String error = "There is no description of the error";
            ResponseBody responseBody = response.errorBody();
            if(responseBody != null) {
                ErrorResponse e = gsonError.fromJson(responseBody.string(), ErrorResponse.class);
                error = e.getError().getCode() + ": " + e.getError().getDescription();
                error += e.getError().getField() != null ? "The field " + e.getError().getField() + " has value " + e.getError().getInstance() : "";
            }
            Log.e(TAG, error);
            int code = response.code();
            if(code >= 300 && code < 400) throw new PlacesDataSourceWSException(PlacesDataSourceWSError.REDIRECTIONS);
            else if(code >= 400 && code < 500) throw new PlacesDataSourceWSException(PlacesDataSourceWSError.CLIENT_ERROR);
            else throw new PlacesDataSourceWSException(PlacesDataSourceWSError.SERVER_ERROR);
        }
    }
}
