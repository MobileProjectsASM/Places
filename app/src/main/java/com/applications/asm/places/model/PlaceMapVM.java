package com.applications.asm.places.model;

public class PlaceMapVM {
    private String name;
    private CoordinatesVM coordinatesVM;
    private String address;
    private String imageUrl;

    public PlaceMapVM(String name, CoordinatesVM coordinatesVM, String address, String imageUrl) {
        this.name = name;
        this.coordinatesVM = coordinatesVM;
        this.address = address;
        this.imageUrl = imageUrl;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
