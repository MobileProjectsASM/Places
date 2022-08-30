package com.applications.asm.data.mapper;

import com.applications.asm.data.PlaceSuggestionQuery;
import com.applications.asm.domain.entities.SuggestedPlace;

import java.util.List;

import javax.inject.Inject;

public class SuggestedPlaceMapperImpl implements SuggestedPlaceMapper {

    @Inject
    public SuggestedPlaceMapperImpl() {

    }

    @Override
    public SuggestedPlace businessToSuggestedPlace(PlaceSuggestionQuery.Business business) {
        String id = business.id() != null ? business.id() : "";

        String name = business.name() != null ? business.name() : "";

        String address = "";
        PlaceSuggestionQuery.Location location = business.location();
        if(location != null) address = getAddress(location);

        String imageUrl = "";
        List<String> photos = business.photos();
        if(photos != null)
            imageUrl = photos.size() > 0 ? photos.get(0) : "";

        return new SuggestedPlace(id, name, address, imageUrl);
    }

    private String getAddress(PlaceSuggestionQuery.Location location) {
        return (location.address1() != null ? location.address1() : "") +
                (location.address2() != null ? ", " + location.address2() : "") +
                (location.address3() != null ? ", " + location.address3() : "") +
                (location.postal_code() != null ? ", " + location.postal_code() : "") +
                (location.city() != null ? " " + location.city() : "") +
                (location.state() != null? ", " + location.state() + "." : "") +
                (location.country() != null ? " " + location.country() : "");
    }
}
