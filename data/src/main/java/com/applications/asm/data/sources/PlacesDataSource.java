package com.applications.asm.data.sources;

import com.applications.asm.data.entity.PlaceDetailsEntity;
import com.applications.asm.data.entity.PlaceEntity;
import com.applications.asm.data.entity.ReviewEntity;

import java.util.List;

public interface PlacesDataSource {
    List<PlaceEntity> getPlacesEntity(String placeToFind, Double longitude, Double latitude, Integer radius, List<String> categories);
    PlaceDetailsEntity getPlaceDetailsEntity(String placeId);
    List<ReviewEntity> getReviewsEntity(String placeId);
}
