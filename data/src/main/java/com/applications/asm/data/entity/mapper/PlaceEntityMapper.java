package com.applications.asm.data.entity.mapper;

import com.applications.asm.data.entity.PlaceEntity;
import com.applications.asm.domain.entities.Place;

public interface PlaceEntityMapper {
    Place getPlaceFromPlaceEntity(PlaceEntity placeEntity);
}
