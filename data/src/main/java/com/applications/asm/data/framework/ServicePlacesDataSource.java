package com.applications.asm.data.framework;

import com.applications.asm.data.entity.PlaceDetailsEntity;
import com.applications.asm.data.entity.PlaceEntity;
import com.applications.asm.data.entity.ReviewEntity;
import com.applications.asm.data.sources.PlacesDataSource;

import java.util.List;

public class ServicePlacesDataSource implements PlacesDataSource {
    @Override
    public List<PlaceEntity> getPlacesEntity(String placeToFind, Double longitude, Double latitude, Integer radius, List<String> categories) {
        return null;
    }

    @Override
    public PlaceDetailsEntity getPlaceDetailsEntity(String placeId) {
        return null;
    }

    @Override
    public List<ReviewEntity> getReviewsEntity(String placeId) {
        return null;
    }
}
