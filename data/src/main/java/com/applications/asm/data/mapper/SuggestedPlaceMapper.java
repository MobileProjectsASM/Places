package com.applications.asm.data.mapper;

import com.applications.asm.data.framework.network.graphql.PlaceSuggestionQuery;
import com.applications.asm.domain.entities.SuggestedPlace;

public interface SuggestedPlaceMapper {
    SuggestedPlace placeLocationToSuggestedPlace(PlaceSuggestionQuery.Business business);
}
