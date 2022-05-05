package com.applications.asm.data.model.mapper;

import com.applications.asm.data.model.PlaceModel;
import com.applications.asm.domain.entities.Place;

public interface PlaceModelMapper {
    Place getPlaceFromPlaceModel(PlaceModel placeModel);
}
