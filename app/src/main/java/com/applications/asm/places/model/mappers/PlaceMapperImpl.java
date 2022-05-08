package com.applications.asm.places.model.mappers;

import com.applications.asm.domain.entities.Category;
import com.applications.asm.domain.entities.Place;
import com.applications.asm.places.model.PlaceM;

import java.util.List;

public class PlaceMapperImpl implements PlaceMapper {

    @Override
    public PlaceM getPlaceMVFromPlace(Place place) {
        String categories = getCategories(place.getCategories());
        return new PlaceM(place.getId(), place.getName(), place.getLatitude(), place.getLongitude(), place.getImageUrl(), categories, place.getAddress());
    }

    private String getCategories(List<Category> categories) {
        StringBuilder categoriesMV = new StringBuilder();
        for(int i = 0; i < categories.size(); i++) {
            if(i == 0)
                categoriesMV.append(categories.get(i).getName());
            else categoriesMV.append(", ").append(categories.get(i).getName());
        }
        return categoriesMV.toString();
    }
}
