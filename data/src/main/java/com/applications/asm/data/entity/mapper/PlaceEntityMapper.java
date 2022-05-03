package com.applications.asm.data.entity.mapper;

import com.applications.asm.data.entity.CategoryEntity;
import com.applications.asm.data.entity.LocationEntity;
import com.applications.asm.data.entity.PlaceEntity;
import com.applications.asm.domain.entities.Category;
import com.applications.asm.domain.entities.Place;

import java.util.ArrayList;
import java.util.List;

public class PlaceEntityMapper {

    public Place getPlaceFromPlaceEntity(PlaceEntity placeEntity) {
        String id = placeEntity.getId();
        String name = placeEntity.getName();
        String imageUrl = placeEntity.getImageUrl();
        Double latitude = placeEntity.getCoordinates().getLatitude();
        Double longitude = placeEntity.getCoordinates().getLongitude();
        List<Category> categories = new ArrayList<>();
        for(CategoryEntity categoryEntity: placeEntity.getCategories())
            categories.add(getCategoryFromCategoryEntity(categoryEntity));
        String address = getAddressFromLocation(placeEntity.getLocation());
        return new Place(id, name, latitude, longitude, imageUrl, categories, address);
    }

    private Category getCategoryFromCategoryEntity(CategoryEntity categoryEntity) {
        return new Category(categoryEntity.getId(), categoryEntity.getName());
    }

    private String getAddressFromLocation(LocationEntity locationEntity) {
        return locationEntity.getAddress() + ", " + locationEntity.getSuburb() + ", " + locationEntity.getZipCode() + " " + locationEntity.getCity() + " " + locationEntity.getCountry();
    }
}
