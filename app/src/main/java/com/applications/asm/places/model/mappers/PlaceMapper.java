package com.applications.asm.places.model.mappers;

import com.applications.asm.domain.entities.Place;
import com.applications.asm.domain.entities.PlaceDetails;
import com.applications.asm.domain.entities.SuggestedPlace;
import com.applications.asm.places.model.PlaceDetailsVM;
import com.applications.asm.places.model.PlaceVM;
import com.applications.asm.places.model.SuggestedPlaceVM;

import java.util.List;

public interface PlaceMapper {
    PlaceVM getPlaceVM(Place place);
    SuggestedPlaceVM getSuggestedPlaceVM(SuggestedPlace suggestedPlace);
    PlaceDetailsVM getPlaceDetailsVM(PlaceDetails placeDetails);
    List<PlaceVM> getPlacesVM(List<Place> places);
    List<SuggestedPlaceVM> getSuggestedPlaces(List<SuggestedPlace> suggestedPlaces);
}
