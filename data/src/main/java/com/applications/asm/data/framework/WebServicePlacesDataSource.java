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

import io.reactivex.rxjava3.core.Single;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

public class WebServicePlacesDataSource implements PlacesDataSourceWS {
    private final PlaceApi placeApi;
    private final String apiKey;
    private final Gson gsonError;

    public WebServicePlacesDataSource(PlaceApi placeApi, String apiKey, Gson gsonError) {
        this.placeApi = placeApi;
        this.apiKey = apiKey;
        this.gsonError = gsonError;
    }

    @Override
    public Single<ResponsePlacesModel> getPlacesModel(String placeToFind, Double longitude, Double latitude, Integer radius, String categories, Integer initIndex, Integer amount) {
        return placeApi.getPlacesModel(apiKey, placeToFind, latitude, longitude, radius, categories, initIndex, amount)
            .doOnError(throwable -> {
                Exception exception = (Exception) throwable;
                if(exception instanceof HttpException) {
                    HttpException httpException = (HttpException) exception;
                    Response<?> response = httpException.response();
                    if(response != null) {
                        ResponseBody responseBody = response.errorBody();
                        String error = "There is no description of the error";
                        if(responseBody != null) {
                            ErrorResponse e = gsonError.fromJson(responseBody.string(), ErrorResponse.class);
                            error = e.getError().getCode() + ": " + e.getError().getDescription();
                            error += e.getError().getField() != null ? "The field " + e.getError().getField() + " has value " + e.getError().getInstance() : "";
                        }
                        Log.e(getClass().getName(), error);
                        int code = response.code();
                        if(code >= 300 && code < 400) throw new PlacesDataSourceWSException(PlacesDataSourceWSError.REDIRECTIONS);
                        else if(code >= 400 && code < 500) throw new PlacesDataSourceWSException(PlacesDataSourceWSError.CLIENT_ERROR);
                        else throw new PlacesDataSourceWSException(PlacesDataSourceWSError.SERVER_ERROR);
                    }
                } else if(exception instanceof IOException)
                    throw new PlacesDataSourceWSException(PlacesDataSourceWSError.NETWORK_ERROR);
            });
    }

    @Override
    public Single<PlaceDetailsModel> getPlaceDetailsModel(String placeId) {
        return placeApi.getPlaceDetailModel(apiKey, placeId)
            .doOnError(throwable -> {
                Exception exception = (Exception) throwable;
                if(exception instanceof HttpException) {
                    HttpException httpException = (HttpException) exception;
                    Response<?> response = httpException.response();
                    if(response != null) {
                        ResponseBody responseBody = response.errorBody();
                        String error = "There is no description of the error";
                        if(responseBody != null) {
                            ErrorResponse e = gsonError.fromJson(responseBody.string(), ErrorResponse.class);
                            error = e.getError().getCode() + ": " + e.getError().getDescription();
                            error += e.getError().getField() != null ? "The field " + e.getError().getField() + " has value " + e.getError().getInstance() : "";
                        }
                        Log.e(getClass().getName(), error);
                        int code = response.code();
                        if(code >= 300 && code < 400) throw new PlacesDataSourceWSException(PlacesDataSourceWSError.REDIRECTIONS);
                        else if(code >= 400 && code < 500) throw new PlacesDataSourceWSException(PlacesDataSourceWSError.CLIENT_ERROR);
                        else throw new PlacesDataSourceWSException(PlacesDataSourceWSError.SERVER_ERROR);
                    }
                } else if(exception instanceof IOException)
                    throw new PlacesDataSourceWSException(PlacesDataSourceWSError.NETWORK_ERROR);
            });
    }

    @Override
    public Single<ResponseReviewsModel> getReviewsModel(String placeId) {
        return placeApi.getReviewsModel(apiKey, placeId)
            .doOnError(throwable -> {
                Exception exception = (Exception) throwable;
                if(exception instanceof HttpException) {
                    HttpException httpException = (HttpException) exception;
                    Response<?> response = httpException.response();
                    if(response != null) {
                        ResponseBody responseBody = response.errorBody();
                        String error = "There is no description of the error";
                        if(responseBody != null) {
                            ErrorResponse e = gsonError.fromJson(responseBody.string(), ErrorResponse.class);
                            error = e.getError().getCode() + ": " + e.getError().getDescription();
                            error += e.getError().getField() != null ? "The field " + e.getError().getField() + " has value " + e.getError().getInstance() : "";
                        }
                        Log.e(getClass().getName(), error);
                        int code = response.code();
                        if(code >= 300 && code < 400) throw new PlacesDataSourceWSException(PlacesDataSourceWSError.REDIRECTIONS);
                        else if(code >= 400 && code < 500) throw new PlacesDataSourceWSException(PlacesDataSourceWSError.CLIENT_ERROR);
                        else throw new PlacesDataSourceWSException(PlacesDataSourceWSError.SERVER_ERROR);
                    }
                } else if(exception instanceof IOException)
                    throw new PlacesDataSourceWSException(PlacesDataSourceWSError.NETWORK_ERROR);
            });
    }

    @Override
    public Single<ResponseSuggestedPlacesModel> getSuggestedPlaces(String word, Double latitude, Double longitude) {
        return placeApi.getSuggestedPlacesModel(apiKey, word, latitude, longitude)
            .doOnError(throwable -> {
                Exception exception = (Exception) throwable;
                if(exception instanceof HttpException) {
                    HttpException httpException = (HttpException) exception;
                    Response<?> response = httpException.response();
                    if(response != null) {
                        ResponseBody responseBody = response.errorBody();
                        String error = "There is no description of the error";
                        if(responseBody != null) {
                            ErrorResponse e = gsonError.fromJson(responseBody.string(), ErrorResponse.class);
                            error = e.getError().getCode() + ": " + e.getError().getDescription();
                            error += e.getError().getField() != null ? "The field " + e.getError().getField() + " has value " + e.getError().getInstance() : "";
                        }
                        Log.e(getClass().getName(), error);
                        int code = response.code();
                        if(code >= 300 && code < 400) throw new PlacesDataSourceWSException(PlacesDataSourceWSError.REDIRECTIONS);
                        else if(code >= 400 && code < 500) throw new PlacesDataSourceWSException(PlacesDataSourceWSError.CLIENT_ERROR);
                        else throw new PlacesDataSourceWSException(PlacesDataSourceWSError.SERVER_ERROR);
                    }
                } else if(exception instanceof IOException)
                    throw new PlacesDataSourceWSException(PlacesDataSourceWSError.NETWORK_ERROR);
            });
    }

    @Override
    public Single<ResponseCategoriesModel> getCategoriesModel(String word, Double latitude, Double longitude, String locale) {
        return placeApi.getCategoriesModel(apiKey, word, latitude, longitude, locale)
            .doOnError(throwable -> {
                Exception exception = (Exception) throwable;
                if(exception instanceof HttpException) {
                    HttpException httpException = (HttpException) exception;
                    Response<?> response = httpException.response();
                    if(response != null) {
                        ResponseBody responseBody = response.errorBody();
                        String error = "There is no description of the error";
                        if(responseBody != null) {
                            ErrorResponse e = gsonError.fromJson(responseBody.string(), ErrorResponse.class);
                            error = e.getError().getCode() + ": " + e.getError().getDescription();
                            error += e.getError().getField() != null ? "The field " + e.getError().getField() + " has value " + e.getError().getInstance() : "";
                        }
                        Log.e(getClass().getName(), error);
                        int code = response.code();
                        if(code >= 300 && code < 400) throw new PlacesDataSourceWSException(PlacesDataSourceWSError.REDIRECTIONS);
                        else if(code >= 400 && code < 500) throw new PlacesDataSourceWSException(PlacesDataSourceWSError.CLIENT_ERROR);
                        else throw new PlacesDataSourceWSException(PlacesDataSourceWSError.SERVER_ERROR);
                    }
                } else if(exception instanceof IOException)
                    throw new PlacesDataSourceWSException(PlacesDataSourceWSError.NETWORK_ERROR);
            });
    }
}
