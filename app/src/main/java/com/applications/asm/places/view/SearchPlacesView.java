package com.applications.asm.places.view;

import com.applications.asm.places.model.CoordinatesVM;
import com.applications.asm.places.model.Resource;
import com.applications.asm.places.model.SuggestedPlaceVM;

import java.util.List;

public interface SearchPlacesView {
    void callbackCoordinates(Resource<CoordinatesVM> resource);
    void callbackSuggestedPlaces(Resource<List<SuggestedPlaceVM>> suggestedPlaces);
}
