package com.applications.asm.data.mapper;

import com.applications.asm.data.PlaceDetailsQuery;
import com.applications.asm.domain.entities.PlaceDetails;

import java.util.Map;

public interface PlaceDetailsMapper {
    PlaceDetails placeDetailsQueryToPlaceDetails(PlaceDetailsQuery.Business business, Map<String, String> days, Map<String, String> prices);
}
