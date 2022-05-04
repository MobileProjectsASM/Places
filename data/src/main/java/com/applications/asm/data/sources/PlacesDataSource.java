package com.applications.asm.data.sources;

import com.applications.asm.data.entity.PlaceDetailsEntity;
import com.applications.asm.data.entity.PlaceEntity;
import com.applications.asm.data.entity.ReviewEntity;
import com.applications.asm.domain.exception.ConnectionServer;

import java.io.IOException;
import java.util.List;

public interface PlacesDataSource {
    List<PlaceEntity> getPlacesEntity(String placeToFind, Double longitude, Double latitude, Integer radius, List<String> categories) throws IOException;
    PlaceDetailsEntity getPlaceDetailsEntity(String placeId) throws IOException;
    List<ReviewEntity> getReviewsEntity(String placeId) throws IOException;
}
