package com.applications.asm.data.framework;

import android.content.Context;

import com.applications.asm.data.R;
import com.applications.asm.data.exception.WebServiceException;
import com.applications.asm.data.model.CategoryModel;
import com.applications.asm.data.model.CoordinatesModel;
import com.applications.asm.data.model.ErrorResponse;
import com.applications.asm.data.model.PlaceDetailsModel;
import com.applications.asm.data.model.PriceModel;
import com.applications.asm.data.model.ResponseCategoriesModel;
import com.applications.asm.data.model.ResponsePlacesModel;
import com.applications.asm.data.model.ResponseReviewsModel;
import com.applications.asm.data.model.ResponseSuggestedPlacesModel;
import com.applications.asm.data.model.SortCriteriaModel;
import com.applications.asm.data.sources.PlacesDataSourceWS;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

public class WebServicePlaces implements PlacesDataSourceWS {
    private final PlaceApi placeApi;
    private final String apiKey;
    private final Gson gsonError;
    private final List<SortCriteriaModel> criteriaModelList;
    private final List<PriceModel> pricesModel;
    private final Context context;

    public WebServicePlaces(PlaceApi placeApi, String apiKey, Gson gsonError, Context context) {
        this.placeApi = placeApi;
        this.apiKey = apiKey;
        this.gsonError = gsonError;
        this.context = context;
        criteriaModelList = initCriteriaModelList();
        pricesModel = initPricesModel();
    }

    @Override
    public Single<ResponsePlacesModel> getPlacesModel(String placeToFind, CoordinatesModel coordinatesModel, Integer radius, List<CategoryModel> categories, SortCriteriaModel sortBy, List<PriceModel> prices, Boolean isOpenNow, Integer initIndex, Integer amount) {
        return placeApi.getPlacesModel(apiKey, placeToFind, coordinatesModel.getLatitude(), coordinatesModel.getLongitude(), radius, getCategories(categories), sortBy.getId(), getPrices(prices), isOpenNow, initIndex, amount)
            .doOnError(this::handleException);
    }

    @Override
    public Single<PlaceDetailsModel> getPlaceDetailsModel(String placeId) {
        return placeApi.getPlaceDetailModel(apiKey, placeId)
            .doOnError(this::handleException);
    }

    @Override
    public Single<ResponseReviewsModel> getReviewsModel(String placeId) {
        return placeApi.getReviewsModel(apiKey, placeId)
            .doOnError(this::handleException);
    }

    @Override
    public Single<ResponseSuggestedPlacesModel> getSuggestedPlaces(String word, CoordinatesModel coordinatesModel) {
        return placeApi.getSuggestedPlacesModel(apiKey, word, coordinatesModel.getLatitude(), coordinatesModel.getLongitude())
            .doOnError(this::handleException);
    }

    @Override
    public Single<ResponseCategoriesModel> getCategoriesModel(String word, CoordinatesModel coordinatesModel, String locale) {
        return placeApi.getCategoriesModel(apiKey, word, coordinatesModel.getLatitude(), coordinatesModel.getLongitude(), locale)
            .doOnError(this::handleException);
    }

    @Override
    public Single<List<SortCriteriaModel>> getCriteriaModel() {
        return Single.just(criteriaModelList);
    }

    @Override
    public Single<List<PriceModel>> getPricesModel() {
        return Single.just(pricesModel);
    }

    private String getCategories(List<CategoryModel> categoriesModel) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < categoriesModel.size(); i++) {
            if(i == categoriesModel.size() - 1)
                stringBuilder.append(categoriesModel.get(i).getId());
            else if(i == 0)
                stringBuilder.append(categoriesModel.get(i).getId());
            else stringBuilder.append(",").append(categoriesModel.get(i).getId());
        }
        return stringBuilder.toString();
    }

    private String getPrices(List<PriceModel> pricesModel) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < pricesModel.size(); i++) {
            if(i == pricesModel.size() - 1)
                stringBuilder.append(getPrice(pricesModel.get(i)));
            else if(i == 0)
                stringBuilder.append(getPrice(pricesModel.get(i)));
            else stringBuilder.append(",").append(getPrice(pricesModel.get(i)));
        }
        return stringBuilder.toString();
    }

    private String getPrice(PriceModel priceModel) {
        String id = priceModel.getId();
        if(id.compareTo("$") == 0) return "1";
        else if(id.compareTo("$$") == 0) return "2";
        else if(id.compareTo("$$$") == 0) return "3";
        else return "4";
    }

    private List<SortCriteriaModel> initCriteriaModelList() {
        List<SortCriteriaModel> sortCriteriaModelList = new ArrayList<>();
        sortCriteriaModelList.add(new SortCriteriaModel("best_match", context.getString(R.string.sort_by_best_match)));
        sortCriteriaModelList.add(new SortCriteriaModel("rating", context.getString(R.string.sort_by_rating)));
        sortCriteriaModelList.add(new SortCriteriaModel("review_count", context.getString(R.string.sort_by_review_count)));
        sortCriteriaModelList.add(new SortCriteriaModel("distance", context.getString(R.string.sort_by_distance)));
        return sortCriteriaModelList;
    }

    private List<PriceModel> initPricesModel() {
        List<PriceModel> pricesModel = new ArrayList<>();
        pricesModel.add(new PriceModel("1", context.getString(R.string.price_cheap)));
        pricesModel.add(new PriceModel("2", context.getString(R.string.price_regular)));
        pricesModel.add(new PriceModel("3", context.getString(R.string.price_expensive)));
        pricesModel.add(new PriceModel("4", context.getString(R.string.price_very_expensive)));
        return pricesModel;
    }

    private void handleException(Throwable throwable) throws Exception {
        Exception exception = (Exception) throwable;
        if(exception instanceof HttpException) {
            HttpException httpException = (HttpException) exception;
            Response<?> response = httpException.response();
            if(response != null) {
                int code = response.code();
                if(code >= 300 && code < 400) throw new WebServiceException("This endpoint has been redirected");
                else if(code >= 400 && code < 500) {
                    String error = "There is no description of the error";
                    ResponseBody responseBody = response.errorBody();
                    if(responseBody != null) {
                        ErrorResponse e = gsonError.fromJson(responseBody.string(), ErrorResponse.class);
                        error = e.getError().getCode() + ": " + e.getError().getDescription();
                        error += e.getError().getField() != null ? "The field " + e.getError().getField() + " has value " + e.getError().getInstance() : "";
                    }
                    throw new WebServiceException("Error when making the request, description:\n" + error);
                }
                else throw new WebServiceException("Server error");
            }
        }
        throw exception;
    }
}
