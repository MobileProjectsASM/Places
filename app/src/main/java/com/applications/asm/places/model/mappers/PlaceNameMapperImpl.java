package com.applications.asm.places.model.mappers;

import com.applications.asm.domain.entities.SuggestedPlace;

public class PlaceNameMapperImpl implements PlaceNameMapper {

    @Override
    public String getPlaceNameFromSuggestedPlace(SuggestedPlace suggestedPlace) {
        return suggestedPlace.getName();
    }
}
