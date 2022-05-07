package com.applications.asm.places.model.mappers;

import com.applications.asm.domain.entities.SuggestedPlace;

public interface PlaceNameMapper {
    String getPlaceNameFromSuggestedPlace(SuggestedPlace suggestedPlace);
}
