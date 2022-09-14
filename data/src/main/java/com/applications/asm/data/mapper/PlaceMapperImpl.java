package com.applications.asm.data.mapper;

import com.applications.asm.data.SearchPlacesQuery;
import com.applications.asm.domain.entities.Category;
import com.applications.asm.domain.entities.Coordinates;
import com.applications.asm.domain.entities.Place;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class PlaceMapperImpl implements PlaceMapper {

    @Inject
    public PlaceMapperImpl() {

    }

    @Override
    public List<Place> placesQueryToPlaces(List<SearchPlacesQuery.Business> businesses) {
        List<Place> places = new ArrayList<>();
        for(SearchPlacesQuery.Business business: businesses)
            places.add(placeQueryToPlace(business));
        return places;
    }

    @Override
    public Place placeQueryToPlace(SearchPlacesQuery.Business business) {
        String id = business.id() != null ? business.id() : "";

        String name = business.name() != null ? business.name() : "";

        String imageUrl = "";
        List<String> photos = business.photos();
        if(photos != null && !photos.isEmpty()) imageUrl = photos.get(0);

        String address = "";
        SearchPlacesQuery.Location location = business.location();
        if(location != null) address = getAddress(location);

        Coordinates coordinates = new Coordinates(0d, 0d);
        SearchPlacesQuery.Coordinates coordinates2 = business.coordinates();
        if(coordinates2 != null) coordinates = getCoordinates(coordinates2);

        List<Category> categories = new ArrayList<>();
        List<SearchPlacesQuery.Category> categoryList = business.categories();
        if(categoryList != null) categories = getCategories(categoryList);

        return new Place(id, name, coordinates, imageUrl, categories, address);
    }

    private Coordinates getCoordinates(SearchPlacesQuery.Coordinates coordinates) {
        Double lat = (double) 0;
        Double latitude = coordinates.latitude();
        if(latitude != null) lat = latitude;

        Double lon = (double) 0;
        Double longitude = coordinates.longitude();
        if(longitude != null) lon = longitude;

        return new Coordinates(lat , lon);
    }

    private List<Category> getCategories(List<SearchPlacesQuery.Category> categories) {
        List<Category> categoryList = new ArrayList<>();
        for(SearchPlacesQuery.Category category : categories)
            categoryList.add(new Category(category.alias(), category.title()));
        return categoryList;
    }

    private String getAddress(SearchPlacesQuery.Location location) {
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
