package com.applications.asm.data.model;

import java.util.List;

public class PlaceModel {
    private String id;
    private String name;
    private String imageUrl;
    private CoordinatesModel coordinatesModel;
    private List<CategoryModel> categories;
    private LocationModel locationModel;

    public PlaceModel() {}

    public PlaceModel(String id, String name, String imageUrl, CoordinatesModel coordinatesModel, List<CategoryModel> categories, LocationModel locationModel) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.coordinatesModel = coordinatesModel;
        this.categories = categories;
        this.locationModel = locationModel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public CoordinatesModel getCoordinatesModel() {
        return coordinatesModel;
    }

    public void setCoordinatesModel(CoordinatesModel coordinatesModel) {
        this.coordinatesModel = coordinatesModel;
    }

    public List<CategoryModel> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryModel> categories) {
        this.categories = categories;
    }

    public LocationModel getLocationModel() {
        return locationModel;
    }

    public void setLocationModel(LocationModel locationModel) {
        this.locationModel = locationModel;
    }
}
