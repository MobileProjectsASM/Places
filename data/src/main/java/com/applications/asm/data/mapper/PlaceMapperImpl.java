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
        return (location.address1() != null ? location.address1() : "") +
                (location.address2() != null ? ", " + location.address2() : "") +
                (location.address3() != null ? ", " + location.address3() : "") +
                (location.postal_code() != null ? ", " + location.postal_code() : "") +
                (location.city() != null ? " " + location.city() : "") +
                (location.state() != null? ", " + location.state() + "." : "") +
                (location.country() != null ? " " + location.country() : "");
    }
}
