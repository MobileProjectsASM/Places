package com.applications.asm.data.mapper;

import com.applications.asm.data.PlaceSuggestionQuery;
import com.applications.asm.domain.entities.SuggestedPlace;

public interface SuggestedPlaceMapper {
    SuggestedPlace businessToSuggestedPlace(PlaceSuggestionQuery.Business business);
}
