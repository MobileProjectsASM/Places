package com.applications.asm.data.repository;

import android.util.Log;

import com.applications.asm.data.exception.WebServiceException;
import com.applications.asm.data.model.mapper.CategoryModelMapper;
import com.applications.asm.data.model.mapper.PlaceModelMapper;
import com.applications.asm.data.model.mapper.PriceModelMapper;
import com.applications.asm.data.model.mapper.ReviewModelMapper;
import com.applications.asm.data.model.mapper.SortCriteriaModelMapper;
import com.applications.asm.data.sources.PlacesDataSourceWS;
import com.applications.asm.domain.entities.Category;
import com.applications.asm.domain.entities.Place;
import com.applications.asm.domain.entities.PlaceDetails;
import com.applications.asm.domain.entities.Price;
import com.applications.asm.domain.entities.Review;
import com.applications.asm.domain.entities.SortCriteria;
import com.applications.asm.domain.entities.SuggestedPlace;
import com.applications.asm.domain.exception.ClientException;
import com.applications.asm.domain.exception.PlacesServiceException;
import com.applications.asm.domain.repository.PlacesRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class PlacesRepositoryImpl implements PlacesRepository {
    private final PlacesDataSourceWS placeDataSourceWs;
    private final PlaceModelMapper placeModelMapper;
    private final ReviewModelMapper reviewModelMapper;
    private final CategoryModelMapper categoryModelMapper;
    private final SortCriteriaModelMapper sortCriteriaModelMapper;
    private final PriceModelMapper priceModelMapper;
    private final static Integer DEFAULT_AMOUNT = 10;
    private Integer totalPages;

    public PlacesRepositoryImpl(
        PlacesDataSourceWS placesDataSourceWs,
        PlaceModelMapper placeModelMapper,
        ReviewModelMapper reviewModelMapper,
        CategoryModelMapper categoryModelMapper,
        SortCriteriaModelMapper sortCriteriaModelMapper,
        PriceModelMapper priceModelMapper
    ) {
        this.placeDataSourceWs = placesDataSourceWs;
        this.placeModelMapper = placeModelMapper;
        this.reviewModelMapper = reviewModelMapper;
        this.categoryModelMapper = categoryModelMapper;
        this.sortCriteriaModelMapper = sortCriteriaModelMapper;
        this.priceModelMapper = priceModelMapper;
    }

    @Override
    public Single<PlaceDetails> getPlaceDetails(String placeId) {
        return Single.fromCallable(() -> {
            if(placeId == null)
                throw new ClientException("Null value was entered");;
            return placeId;
        }).flatMap(
            placeDataSourceWs::getPlaceDetailsModel
        ).map(placeDetailsModel -> {
            if(placeDetailsModel != null)
                return placeModelMapper.getPlaceDetails(placeDetailsModel);
            throw new PlacesServiceException("Response null");
        })
        .doOnError(this::handleException);
    }

    @Override
    public Single<List<Place>> getPlaces(String placeToFind, Double longitude, Double latitude, Integer radius, List<Category> categories, SortCriteria sortBy, List<Price> prices, Boolean isOpenNow, Integer page) {
        return Single.fromCallable(() -> {
            if(placeToFind == null || longitude == null || latitude == null || radius == null || categories == null || page == null)
                throw new ClientException("Null value was entered");
            return true;
        }).flatMap(params -> {
            if(page == 0) {
                return placeDataSourceWs.getPlacesModel(placeToFind, longitude, latitude, radius, categoryModelMapper.getCategoriesModel(categories), sortCriteriaModelMapper.getSortCriteriaModel(sortBy), priceModelMapper.getPricesModel(prices), isOpenNow,0, DEFAULT_AMOUNT)
                    .flatMap(responsePlacesModel -> Single.fromCallable(() -> {
                        if(responsePlacesModel != null) {
                            int total = responsePlacesModel.getTotal();
                            totalPages = total % DEFAULT_AMOUNT == 0 ? total / 10 : total / 10 + 1;
                            return responsePlacesModel;
                        }
                        throw new PlacesServiceException("Response null");
                    }));
            } else if(page > 0 && page < totalPages)
                return placeDataSourceWs.getPlacesModel(placeToFind, longitude, latitude, radius, categoryModelMapper.getCategoriesModel(categories), sortCriteriaModelMapper.getSortCriteriaModel(sortBy), priceModelMapper.getPricesModel(prices), isOpenNow, (page - 1) * DEFAULT_AMOUNT , DEFAULT_AMOUNT);
            throw new PlacesServiceException("Page out of range");
        }).map(responsePlacesModel -> {
            if(responsePlacesModel != null)
                return placeModelMapper.getPlaces(responsePlacesModel.getPlaces());
            throw new PlacesServiceException("Response null");
        }).doOnError(this::handleException);
    }

    @Override
    public Single<List<Review>> getReviews(String placeId) {
        return Single.fromCallable(() -> {
            if(placeId == null)
                throw new ClientException("Null value was entered");
            return placeId;
        }).flatMap(
            placeDataSourceWs::getReviewsModel
        ).map(responseReviewsModel -> {
            if(responseReviewsModel != null)
                return reviewModelMapper.getReviews(responseReviewsModel.getReviewModels());
            throw new PlacesServiceException("Response null");
        }).doOnError(this::handleException);

    }

    @Override
    public Single<List<SuggestedPlace>> getSuggestedPlaces(String word, Double longitude, Double latitude) {
        return Single.fromCallable(() -> {
            if(word == null || latitude == null || longitude == null)
                throw new ClientException("Null value was entered");
            return true;
        }).flatMap(
            value -> placeDataSourceWs.getSuggestedPlaces(word, longitude, latitude)
        ).map(responseSuggestedPlacesModel -> {
            if(responseSuggestedPlacesModel != null)
                return placeModelMapper.getSuggestedPlaces(responseSuggestedPlacesModel.getSuggestPlacesModel());
            throw new PlacesServiceException("Response null");
        }).doOnError(this::handleException);
    }

    @Override
    public Single<List<Category>> getCategories(String word, Double longitude, Double latitude, String locale) {
        return Single.fromCallable(() -> {
            if(locale == null)
                throw new ClientException("Null value was entered");
            return true;
        }).flatMap(
            value -> placeDataSourceWs.getCategoriesModel(word, longitude, latitude, locale)
        ).map(responseCategoriesModel -> {
            if(responseCategoriesModel != null)
                return categoryModelMapper.getCategories(responseCategoriesModel.getCategoryModelList());
            throw new PlacesServiceException("Response null");
        }).doOnError(this::handleException);
    }

    @Override
    public Single<List<SortCriteria>> getSortCriteria() {
        return placeDataSourceWs.getCriteriaModel()
                .map(sortCriteriaModelMapper::getSortCriteriaList);
    }

    @Override
    public Single<List<Price>> getPrices() {
        return placeDataSourceWs.getPricesModel()
                .map(priceModelMapper::getPrices);
    }

    private void handleException(Throwable throwable) throws Exception {
        Exception exception = (Exception) throwable;
        if(exception instanceof WebServiceException) {
            Log.i(getClass().getName(), exception.getMessage());
            throw new PlacesServiceException("Error consuming PlacesRepository service");
        }
        else throw exception;
    }
}
