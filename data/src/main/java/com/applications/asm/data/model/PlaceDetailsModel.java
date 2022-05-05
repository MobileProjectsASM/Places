package com.applications.asm.data.model;

import java.util.List;

public class PlaceDetailsModel {
    private String id;
    private String name;
    private CoordinatesModel coordinatesModel;
    private String imageUrl;
    private Double rating;
    private String price;
    private String phoneNumber;
    private Integer reviewCount;
    private List<WorkingHoursModel> workingHoursModelDays;
    private Boolean isOpen;

    public PlaceDetailsModel() {}

    public PlaceDetailsModel(String id, String name, CoordinatesModel coordinatesModel, String imageUrl, Double rating, String price, String phoneNumber, Integer reviewCount, List<WorkingHoursModel> workingHoursModelDays, Boolean isOpen) {
        this.id = id;
        this.name = name;
        this.coordinatesModel = coordinatesModel;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.price = price;
        this.phoneNumber = phoneNumber;
        this.reviewCount = reviewCount;
        this.workingHoursModelDays = workingHoursModelDays;
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

    public CoordinatesModel getCoordinatesModel() {
        return coordinatesModel;
    }

    public void setCoordinatesModel(CoordinatesModel coordinatesModel) {
        this.coordinatesModel = coordinatesModel;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public List<WorkingHoursModel> getWorkingHoursModelDays() {
        return workingHoursModelDays;
    }

    public void setWorkingHoursModelDays(List<WorkingHoursModel> workingHoursModelDays) {
        this.workingHoursModelDays = workingHoursModelDays;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }
}
