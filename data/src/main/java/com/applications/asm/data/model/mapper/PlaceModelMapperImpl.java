package com.applications.asm.data.model.mapper;

import com.applications.asm.data.model.CategoryModel;
import com.applications.asm.data.model.LocationModel;
import com.applications.asm.data.model.PlaceModel;
import com.applications.asm.domain.entities.Category;
import com.applications.asm.domain.entities.Place;

import java.util.ArrayList;
import java.util.List;

public class PlaceModelMapperImpl implements PlaceModelMapper {
    @Override
    public Place getPlaceFromPlaceModel(PlaceModel placeModel) {
        String id = placeModel.getId();
        String name = placeModel.getName();
        String imageUrl = placeModel.getImageUrl();
        Double latitude = placeModel.getCoordinatesModel().getLatitude();
        Double longitude = placeModel.getCoordinatesModel().getLongitude();
        List<Category> categories = new ArrayList<>();
        for(CategoryModel categoryModel : placeModel.getCategories())
            categories.add(getCategoryFromCategoryModel(categoryModel));
        String address = getAddressFromLocation(placeModel.getLocationModel());
        return new Place(id, name, latitude, longitude, imageUrl, categories, address);
    }

    private Category getCategoryFromCategoryModel(CategoryModel categoryModel) {
        return new Category(categoryModel.getId(), categoryModel.getName());
    }

    private String getAddressFromLocation(LocationModel locationModel) {
        return locationModel.getAddress() +
                (locationModel.getSuburb().compareTo("") == 0 ? "" : ", " + locationModel.getSuburb()) +
                (locationModel.getZipCode().compareTo("") == 0 ? "" : ", " + locationModel.getZipCode()) +
                (locationModel.getCity().compareTo("") == 0 ? "" : " " + locationModel.getCity()) +
                (locationModel.getState().compareTo("") == 0 ? "" : ", " + locationModel.getState() + ".") +
                (locationModel.getCountry().compareTo("") == 0 ? "" : " " + locationModel.getCountry());
    }
}
