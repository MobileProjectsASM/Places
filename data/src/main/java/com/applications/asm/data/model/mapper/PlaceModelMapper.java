package com.applications.asm.data.model.mapper;

import com.applications.asm.data.model.PlaceDetailsModel;
import com.applications.asm.data.model.PlaceModel;
import com.applications.asm.domain.entities.Place;
import com.applications.asm.domain.entities.PlaceDetails;

public interface PlaceModelMapper {
    Place getPlaceFromPlaceModel(PlaceModel placeModel);
    PlaceDetails getPlaceDetailsFromPlaceDetailsModel(PlaceDetailsModel placeDetailsModel);
}
