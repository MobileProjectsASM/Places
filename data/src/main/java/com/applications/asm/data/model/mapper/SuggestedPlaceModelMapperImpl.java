package com.applications.asm.data.model.mapper;

import com.applications.asm.data.model.SuggestedPlaceModel;
import com.applications.asm.domain.entities.SuggestedPlace;

public class SuggestedPlaceModelMapperImpl implements SuggestedPlaceModelMapper{
    @Override
    public SuggestedPlace getSuggestedPlaceFromSuggestedPlaceModel(SuggestedPlaceModel suggestedPlaceModel) {
        return new SuggestedPlace(suggestedPlaceModel.getId(), suggestedPlaceModel.getName());
    }
}
