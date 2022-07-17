package com.applications.asm.data.model.mapper;

import com.applications.asm.data.model.LocationModel;
import com.applications.asm.data.model.PlaceDetailsModel;
import com.applications.asm.data.model.PlaceModel;
import com.applications.asm.data.model.SuggestedPlaceModel;
import com.applications.asm.domain.entities.Place;
import com.applications.asm.domain.entities.PlaceDetails;
import com.applications.asm.domain.entities.SuggestedPlace;

import java.util.List;

public interface PlaceModelMapper {
    Place getPlace(PlaceModel placeModel);
    PlaceDetails getPlaceDetails(PlaceDetailsModel placeDetailsModel);
    List<Place> getPlaces(List<PlaceModel> placesModel);
    SuggestedPlace getSuggestedPlace(SuggestedPlaceModel suggestedPlaceModel, LocationModel locationModel);
}
