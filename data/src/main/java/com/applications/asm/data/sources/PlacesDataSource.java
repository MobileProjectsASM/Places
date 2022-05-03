package com.applications.asm.data.sources;

import com.applications.asm.data.entity.PlaceEntity;

import java.util.List;

public interface PlacesDataSource {
    List<PlaceEntity> getPlacesEntity(String placeToFind, Double longitude, Double latitude, Integer radius, List<String> categories);
}
