package com.applications.asm.places.model.mappers;

import com.applications.asm.domain.entities.Place;
import com.applications.asm.places.model.PlaceMV;

public interface PlaceMVMapper {
    PlaceMV getPlaceMVFromPlace(Place place);
}
