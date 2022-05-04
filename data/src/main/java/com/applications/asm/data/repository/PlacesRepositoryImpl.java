package com.applications.asm.data.repository;

import android.util.Log;

import com.applications.asm.data.entity.PlaceDetailsEntity;
import com.applications.asm.data.entity.PlaceEntity;
import com.applications.asm.data.entity.ReviewEntity;
import com.applications.asm.data.entity.mapper.PlaceDetailsEntityMapper;
import com.applications.asm.data.entity.mapper.PlaceEntityMapper;
import com.applications.asm.data.entity.mapper.ReviewEntityMapper;
import com.applications.asm.data.sources.PlacesDataSource;
import com.applications.asm.domain.entities.Place;
import com.applications.asm.domain.entities.PlaceDetails;
import com.applications.asm.domain.entities.Review;
import com.applications.asm.domain.exception.ConnectionServer;
import com.applications.asm.domain.repository.PlacesRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlacesRepositoryImpl implements PlacesRepository {
    private final PlacesDataSource placeDataSource;
    private final PlaceEntityMapper placeEntityMapper;
    private final PlaceDetailsEntityMapper placeDetailsEntityMapper;
    private final ReviewEntityMapper reviewEntityMapper;
    private final String TAG = "PlacesRepositoryImpl";

    public PlacesRepositoryImpl(PlacesDataSource placeDataSource, PlaceEntityMapper placeEntityMapper, PlaceDetailsEntityMapper placeDetailsEntityMapper, ReviewEntityMapper reviewEntityMapper) {
        this.placeDataSource = placeDataSource;
        this.placeEntityMapper = placeEntityMapper;
        this.placeDetailsEntityMapper = placeDetailsEntityMapper;
        this.reviewEntityMapper = reviewEntityMapper;
    }

    @Override
    public PlaceDetails getPlaceDetails(String placeId) throws ConnectionServer {
        try {
            PlaceDetailsEntity placeDetails = placeDataSource.getPlaceDetailsEntity(placeId);
            if(placeDetails != null)
                return placeDetailsEntityMapper.getPlaceDetailsFromPlaceDetailsEntity(placeDetails);
            return null;
        } catch (IOException ioException) {
            throw new ConnectionServer(ioException.getMessage());
        } catch (RuntimeException runtimeException) {
            Log.e(TAG, runtimeException.getMessage());
            return null;
        }
    }

    @Override
    public List<Place> getPlaces(String placeToFind, Double longitude, Double latitude, Integer radius, List<String> categories) throws ConnectionServer {
        try {
            List<Place> places = new ArrayList<>();
            List<PlaceEntity> placesEntity = placeDataSource.getPlacesEntity(placeToFind, longitude, latitude, radius, categories);
            for (PlaceEntity placeEntity : placesEntity)
                places.add(placeEntityMapper.getPlaceFromPlaceEntity(placeEntity));
            return places;
        } catch (IOException ioException) {
            throw new ConnectionServer(ioException.getMessage());
        } catch (RuntimeException runtimeException) {
            Log.e(TAG, runtimeException.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public List<Review> getReviews(String placeId) throws ConnectionServer {
        try {
            List<Review> reviews = new ArrayList<>();
            List<ReviewEntity> reviewsEntities = placeDataSource.getReviewsEntity(placeId);
            for (ReviewEntity reviewEntity : reviewsEntities)
                reviews.add(reviewEntityMapper.getReviewFromReviewEntity(reviewEntity));
            return reviews;
        } catch (IOException ioException) {
            throw new ConnectionServer(ioException.getMessage());
        } catch (RuntimeException runtimeException) {
            Log.e(TAG, runtimeException.getMessage());
            return new ArrayList<>();
        }
    }
}
