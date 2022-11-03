package com.applications.asm.places.model;

public class PlaceVM {
    private String id;
    private String name;
    private CoordinatesVM coordinatesVM;
    private String imageUrl;
    private String categories;
    private String address;

    public PlaceVM(String id, String name, CoordinatesVM coordinatesVM, String imageUrl, String categories, String address) {
        this.id = id;
        this.name = name;
        this.coordinatesVM = coordinatesVM;
        this.imageUrl = imageUrl;
        this.categories = categories;
        this.address = address;
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

    public CoordinatesVM getCoordinatesVM() {
        return coordinatesVM;
    }

    public void setCoordinatesVM(CoordinatesVM coordinatesVM) {
        this.coordinatesVM = coordinatesVM;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
