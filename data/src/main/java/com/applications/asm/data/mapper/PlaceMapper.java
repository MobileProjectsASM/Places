package com.applications.asm.data.mapper;

import com.applications.asm.data.SearchPlacesQuery;
import com.applications.asm.domain.entities.Place;

import java.util.List;

public interface PlaceMapper {
    List<Place> placesQueryToPlaces(List<SearchPlacesQuery.Business> businesses);
    Place placeQueryToPlace(SearchPlacesQuery.Business business);
}
