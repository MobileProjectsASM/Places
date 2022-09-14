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
        String add1 = location.address1() != null ? location.address1() : "";

        String add2 = "";
        String address2 = location.address2();
        if(address2 != null) if(!address2.isEmpty()) add2 = ", " + address2;

        String add3 = "";
        String address3 = location.address3();
        if(address3 != null) if(!address3.isEmpty()) add3 = ", " + address3;

        String pc = "";
        String postalCode = location.postal_code();
        if(postalCode != null) if(!postalCode.isEmpty()) pc = ", " + postalCode;

        String city = "";
        String city2 = location.city();
        if(city2 != null) if(!city2.isEmpty()) city = ", " + city2;

        String state = "";
        String state2 = location.state();
        if(state2 != null) if(!state2.isEmpty()) state = ", " + state2 + ".";

        String country = "";
        String country2 = location.country();
        if(country2 != null) if(!country2.isEmpty()) country = " " + location.country();

        return add1 + add2 + add3 + pc + city + state + country;
    }
}
