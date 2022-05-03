package com.applications.asm.data.entity;

import java.util.List;

public class PlaceEntity {
    private String id;
    private String name;
    private String imageUrl;
    private CoordinatesEntity coordinatesEntity;
    private List<CategoryEntity> categories;
    private LocationEntity locationEntity;

    public PlaceEntity() {}

    public PlaceEntity(String id, String name, String imageUrl, CoordinatesEntity coordinatesEntity, List<CategoryEntity> categories, LocationEntity locationEntity) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.coordinatesEntity = coordinatesEntity;
        this.categories = categories;
        this.locationEntity = locationEntity;
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

    public CoordinatesEntity getCoordinates() {
        return coordinatesEntity;
    }

    public void setCoordinates(CoordinatesEntity coordinatesEntity) {
        this.coordinatesEntity = coordinatesEntity;
    }

    public List<CategoryEntity> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryEntity> categories) {
        this.categories = categories;
    }

    public LocationEntity getLocation() {
        return locationEntity;
    }

    public void setLocation(LocationEntity locationEntity) {
        this.locationEntity = locationEntity;
    }
}
