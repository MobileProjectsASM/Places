package com.applications.asm.data.repository;

import android.util.Log;

import com.applications.asm.data.model.PlaceDetailsModel;
import com.applications.asm.data.model.PlaceModel;
import com.applications.asm.data.model.ResponsePlacesModel;
import com.applications.asm.data.model.ResponseReviewsModel;
import com.applications.asm.data.model.ResponseSuggestedPlacesModel;
import com.applications.asm.data.model.ReviewModel;
import com.applications.asm.data.model.SuggestedPlaceModel;
import com.applications.asm.data.model.mapper.PlaceDetailsModelMapper;
import com.applications.asm.data.model.mapper.PlaceModelMapper;
import com.applications.asm.data.model.mapper.ReviewModelMapper;
import com.applications.asm.data.model.mapper.SuggestedPlaceModelMapper;
import com.applications.asm.data.sources.PlacesDataSource;
import com.applications.asm.domain.entities.Place;
import com.applications.asm.domain.entities.PlaceDetails;
import com.applications.asm.domain.entities.Review;
import com.applications.asm.domain.entities.SuggestedPlace;
import com.applications.asm.domain.exception.ConnectionServer;
import com.applications.asm.domain.repository.PlacesRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlacesRepositoryImpl implements PlacesRepository {
    private final PlacesDataSource placeDataSource;
    private final PlaceModelMapper placeModelMapper;
    private final PlaceDetailsModelMapper placeDetailsModelMapper;
    private final ReviewModelMapper reviewModelMapper;
    private final SuggestedPlaceModelMapper suggestedPlaceModelMapper;
    private final String TAG = "PlacesRepositoryImpl";

    public PlacesRepositoryImpl(
        PlacesDataSource placeDataSource,
        PlaceModelMapper placeModelMapper,
        PlaceDetailsModelMapper placeDetailsModelMapper,
        ReviewModelMapper reviewModelMapper,
        SuggestedPlaceModelMapper suggestedPlaceModelMapper
    ) {
        this.placeDataSource = placeDataSource;
        this.placeModelMapper = placeModelMapper;
        this.placeDetailsModelMapper = placeDetailsModelMapper;
        this.reviewModelMapper = reviewModelMapper;
        this.suggestedPlaceModelMapper = suggestedPlaceModelMapper;
    }

    @Override
    public List<Place> getPlaces(String placeToFind, Double longitude, Double latitude, Integer radius, List<String> categories) throws ConnectionServer {
        try {
            List<Place> places = new ArrayList<>();
            ResponsePlacesModel responsePlacesModel = placeDataSource.getPlacesModel(placeToFind, longitude, latitude, radius, categories);
            if(responsePlacesModel != null)  {
                for (PlaceModel placeModel : responsePlacesModel.getPlaces())
                    places.add(placeModelMapper.getPlaceFromPlaceModel(placeModel));
                return places;
            }
            return new ArrayList<>();
        } catch (IOException ioException) {
            throw new ConnectionServer(ioException.getMessage());
        } catch (RuntimeException runtimeException) {
            Log.e(TAG, runtimeException.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public PlaceDetails getPlaceDetails(String placeId) throws ConnectionServer {
        try {
            PlaceDetailsModel placeDetails = placeDataSource.getPlaceDetailsModel(placeId);
            if(placeDetails != null)
                return placeDetailsModelMapper.getPlaceDetailsFromPlaceDetailsModel(placeDetails);
            return null;
        } catch (IOException ioException) {
            throw new ConnectionServer(ioException.getMessage());
        } catch (RuntimeException runtimeException) {
            Log.e(TAG, runtimeException.getMessage());
            return null;
        }
    }

    @Override
    public List<Review> getReviews(String placeId) throws ConnectionServer {
        try {
            List<Review> reviews = new ArrayList<>();
            ResponseReviewsModel responseReviewsModel = placeDataSource.getReviewsModel(placeId);
            if(responseReviewsModel != null) {
                for (ReviewModel reviewModel : responseReviewsModel.getReviewModels())
                    reviews.add(reviewModelMapper.getReviewFromReviewModel(reviewModel));
                return reviews;
            }
            return new ArrayList<>();
        } catch (IOException ioException) {
            throw new ConnectionServer(ioException.getMessage());
        } catch (RuntimeException runtimeException) {
            Log.e(TAG, runtimeException.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<SuggestedPlace> getSuggestedPlaces(String word, Double longitude, Double latitude) throws ConnectionServer {
        try {
            List<SuggestedPlace> suggestedPlaces = new ArrayList<>();
            ResponseSuggestedPlacesModel responseSuggestedPlacesModel = placeDataSource.getSuggestedPlaces(word, longitude, latitude);
            if(responseSuggestedPlacesModel != null) {
                for(SuggestedPlaceModel suggestedPlaceModel: responseSuggestedPlacesModel.getSuggestPlacesModel())
                    suggestedPlaces.add(suggestedPlaceModelMapper.getSuggestedPlaceFromSuggestedPlaceModel(suggestedPlaceModel));
            }
            return new ArrayList<>();
        } catch (IOException ioException) {
            throw new ConnectionServer(ioException.getMessage());
        } catch (RuntimeException runtimeException) {
            Log.e(TAG, runtimeException.getMessage());
            return new ArrayList<>();
        }
    }
}
