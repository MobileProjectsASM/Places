package com.applications.asm.data.entity.mapper;

import com.applications.asm.data.entity.PlaceDetailsEntity;
import com.applications.asm.domain.entities.PlaceDetails;

public interface PlaceDetailsEntityMapper {
    PlaceDetails getPlaceDetailsFromPlaceDetailsEntity(PlaceDetailsEntity placeDetailsEntity);
}
