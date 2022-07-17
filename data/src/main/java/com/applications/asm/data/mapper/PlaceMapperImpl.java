package com.applications.asm.data.mapper;

import com.applications.asm.data.framework.network.graphql.SearchPlacesQuery;
import com.applications.asm.domain.entities.Category;
import com.applications.asm.domain.entities.Coordinates;
import com.applications.asm.domain.entities.Place;

import java.util.ArrayList;
import java.util.List;

public class PlaceMapperImpl implements PlaceMapper {

    @Override
    public List<Place> placesQueryToPlaces(List<SearchPlacesQuery.Business> businesses) {
        List<Place> places = new ArrayList<>();
        for(SearchPlacesQuery.Business business: businesses)
            places.add(placeQueryToPlace(business));
        return places;
    }

    @Override
    public Place placeQueryToPlace(SearchPlacesQuery.Business business) {
        String id = business.id != null ? business.id : "";
        String name = business.name != null ? business.name : "";
        String imageUrl = !business.photos.isEmpty() ? business.photos.get(0) : "";
        String address = business.location != null ? getAddress(business.location) : "";
        Coordinates coordinates = getCoordinates(business.coordinates);
        List<Category> categories = business.categories != null ? getCategories(business.categories) : new ArrayList<>();
        return new Place(id, name, coordinates, imageUrl, categories, address);
    }

    private Coordinates getCoordinates(SearchPlacesQuery.Coordinates coordinates) {
        Double latitude = coordinates != null && coordinates.latitude != null ? coordinates.latitude : 0;
        Double longitude = coordinates != null && coordinates.longitude != null ? coordinates.longitude : 0;
        return new Coordinates(latitude , longitude);
    }

    private List<Category> getCategories(List<SearchPlacesQuery.Category> categories) {
        List<Category> categoryList = new ArrayList<>();
        for(SearchPlacesQuery.Category category : categories)
            categoryList.add(new Category(category.alias, category.title));
        return categoryList;
    }

    private String getAddress(SearchPlacesQuery.Location location) {
        return (location.address1 != null ? location.address1 : "") +
                (location.address2 != null ? ", " + location.address2 : "") +
                (location.address3 != null ? ", " + location.address3 : "") +
                (location.postal_code != null ? ", " + location.postal_code : "") +
                (location.city != null ? " " + location.city : "") +
                (location.state != null? ", " + location.state + "." : "") +
                (location.country != null ? " " + location.country : "");
    }
}
