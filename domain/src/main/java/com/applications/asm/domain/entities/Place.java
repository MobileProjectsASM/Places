package com.applications.asm.domain.entities;

import java.util.List;

public class Place {
    private String id;
    private String name;
    private Location location;
    private String imageUrl;
    private List<String> categories;
    private String address;

    public Place(String id, String name, Location location, String imageUrl, List<String> categories, String address) {
        this.id = id;
        this.name = name;
        this.location = location;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}