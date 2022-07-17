package com.applications.asm.domain.entities;

import java.util.List;

public class Place {
    private String id;
    private String name;
    private Coordinates coordinates;
    private String imageUrl;
    private List<Category> categories;
    private String address;

    public Place(String id, String name, Coordinates coordinates, String imageUrl, List<Category> categories, String address) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
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

    public Coordinates getLocation() {
        return coordinates;
    }

    public void setLocation(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}