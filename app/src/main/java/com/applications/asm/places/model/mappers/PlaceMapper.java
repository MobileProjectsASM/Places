package com.applications.asm.places.model.mappers;

import com.applications.asm.domain.entities.Place;
import com.applications.asm.places.model.PlaceM;

public interface PlaceMapper {
    PlaceM getPlaceMVFromPlace(Place place);
}
