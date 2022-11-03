package com.applications.asm.places.model;

import java.util.List;

public class PlaceDetailsVM {
    private String id;
    private String name;
    private String imageUrl;
    private String address;
    private CoordinatesVM coordinatesVM;
    private Double rating;
    private PriceVM price;
    private String categories;
    private String phoneNumber;
    private Integer reviewsCounter;
    private List<ScheduleVM> schedule;
    private Boolean isOpen;

    public PlaceDetailsVM(String id, String name, String imageUrl, String address, CoordinatesVM coordinatesVM, Double rating, PriceVM price, String categories, String phoneNumber, Integer reviewsCounter, List<ScheduleVM> schedule, Boolean isOpen) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.address = address;
        this.coordinatesVM = coordinatesVM;
        this.rating = rating;
        this.price = price;
        this.categories = categories;
        this.phoneNumber = phoneNumber;
        this.reviewsCounter = reviewsCounter;
        this.schedule = schedule;
        this.isOpen = isOpen;
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

    public CoordinatesVM getCoordinatesVM() {
        return coordinatesVM;
    }

    public void setCoordinatesVM(CoordinatesVM coordinatesVM) {
        this.coordinatesVM = coordinatesVM;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public PriceVM getPrice() {
        return price;
    }

    public void setPrice(PriceVM price) {
        this.price = price;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getReviewsCounter() {
        return reviewsCounter;
    }

    public void setReviewsCounter(Integer reviewsCounter) {
        this.reviewsCounter = reviewsCounter;
    }

    public List<ScheduleVM> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<ScheduleVM> schedule) {
        this.schedule = schedule;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }
}
