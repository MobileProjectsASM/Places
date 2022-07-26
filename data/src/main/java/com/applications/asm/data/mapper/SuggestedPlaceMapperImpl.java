package com.applications.asm.data.mapper;

import com.applications.asm.data.framework.network.graphql.PlaceSuggestionQuery;
import com.applications.asm.domain.entities.SuggestedPlace;

import javax.inject.Inject;

public class SuggestedPlaceMapperImpl implements SuggestedPlaceMapper {

    @Inject
    public SuggestedPlaceMapperImpl() {

    }

    @Override
    public SuggestedPlace placeLocationToSuggestedPlace(PlaceSuggestionQuery.Business business) {
        String id = business.id != null ? business.id : "";
        String name = business.name != null ? business.name : "";
        String address = business.location != null ? getAddress(business.location) : "";
        String imageUrl = business.photos != null && business.photos.size() > 0 ? business.photos.get(0) : "";
        return new SuggestedPlace(id, name, address, imageUrl);
    }

    private String getAddress(PlaceSuggestionQuery.Location location) {
        return (location.address1 != null ? location.address1 : "") +
                (location.address2 != null ? ", " + location.address2 : "") +
                (location.address3 != null ? ", " + location.address3 : "") +
                (location.postal_code != null ? ", " + location.postal_code : "") +
                (location.city != null ? " " + location.city : "") +
                (location.state != null? ", " + location.state + "." : "") +
                (location.country != null ? " " + location.country : "");
    }
}
