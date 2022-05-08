package com.applications.asm.places.model.mappers;

import com.applications.asm.domain.entities.PlaceDetails;
import com.applications.asm.places.model.PlaceDetailsM;

public interface PlaceDetailsMapper {

    PlaceDetailsM getPlaceDetailsMFromPlaceDetails(PlaceDetails placeDetails);
}
