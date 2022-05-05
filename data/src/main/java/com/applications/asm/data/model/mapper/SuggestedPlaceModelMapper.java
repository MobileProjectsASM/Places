package com.applications.asm.data.model.mapper;

import com.applications.asm.data.model.SuggestedPlaceModel;
import com.applications.asm.domain.entities.SuggestedPlace;


public interface SuggestedPlaceModelMapper {
    SuggestedPlace getSuggestedPlaceFromSuggestedPlaceModel(SuggestedPlaceModel suggestedPlaceModel);
}
