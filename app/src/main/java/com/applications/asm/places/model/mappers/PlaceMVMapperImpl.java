package com.applications.asm.places.model.mappers;

import com.applications.asm.domain.entities.Category;
import com.applications.asm.domain.entities.Place;
import com.applications.asm.places.model.PlaceMV;

import java.util.ArrayList;
import java.util.List;

public class PlaceMVMapperImpl implements PlaceMVMapper {

    @Override
    public PlaceMV getPlaceMVFromPlace(Place place) {
        List<String> categories = getCategories(place.getCategories());
        return new PlaceMV(place.getId(), place.getName(), place.getLatitude(), place.getLongitude(), place.getImageUrl(), categories, place.getAddress());
    }

    private List<String> getCategories(List<Category> categories) {
        List<String> listCategory = new ArrayList<>();
        for(Category category: categories)
            listCategory.add(category.getName());
        return listCategory;
    }
}
