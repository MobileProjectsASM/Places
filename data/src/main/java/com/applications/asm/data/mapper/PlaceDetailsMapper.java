package com.applications.asm.data.mapper;

import com.applications.asm.data.framework.network.graphql.PlaceDetailsQuery;
import com.applications.asm.domain.entities.PlaceDetails;

public interface PlaceDetailsMapper {
    PlaceDetails placeDetailsQueryToPlaceDetails(PlaceDetailsQuery.Business business);
}
