package com.applications.asm.data.repository;

import com.applications.asm.data.entity.PlaceEntity;
import com.applications.asm.data.entity.mapper.PlaceEntityMapper;
import com.applications.asm.data.sources.PlacesDataSource;
import com.applications.asm.domain.entities.Place;
import com.applications.asm.domain.entities.PlaceDetails;
import com.applications.asm.domain.entities.Review;
import com.applications.asm.domain.repository.PlacesRepository;

import java.util.ArrayList;
import java.util.List;

public class PlacesRepositoryImpl implements PlacesRepository {
    private final PlacesDataSource placeDataSource;
    private final PlaceEntityMapper placeEntityMapper;

    public PlacesRepositoryImpl(PlacesDataSource placeDataSource, PlaceEntityMapper placeEntityMapper) {
        this.placeDataSource = placeDataSource;
        this.placeEntityMapper = placeEntityMapper;
    }

    @Override
    public PlaceDetails getPlaceDetails(String placeId) {
        return null;
    }

    @Override
    public List<Place> getPlaces(String placeToFind, Double longitude, Double latitude, Integer radius, List<String> categories) {
        List<Place> places = new ArrayList<>();
        List<PlaceEntity> placesEntity = placeDataSource.getPlacesEntity(placeToFind, longitude, latitude, radius, categories);
        for(PlaceEntity placeEntity: placesEntity)
            places.add(placeEntityMapper.getPlaceFromPlaceEntity(placeEntity));
        return places;
    }

    @Override
    public List<Review> getReviews(String placeId) {
        return null;
    }
}
