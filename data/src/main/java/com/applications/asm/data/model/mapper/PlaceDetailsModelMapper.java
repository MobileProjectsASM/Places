package com.applications.asm.data.model.mapper;

import com.applications.asm.data.model.PlaceDetailsModel;
import com.applications.asm.domain.entities.PlaceDetails;

public interface PlaceDetailsModelMapper {
    PlaceDetails getPlaceDetailsFromPlaceDetailsModel(PlaceDetailsModel placeDetailsModel);
}
