package com.applications.asm.data.model;

import java.util.List;

public class PlaceDetailsModel extends PlaceModel {
    private Double rating;
    private String price;
    private String phoneNumber;
    private Integer reviewCount;
    private List<WorkingHoursModel> workingHoursModelDays;
    private Boolean isOpen;

    public PlaceDetailsModel() {
        super();
    }

    public PlaceDetailsModel(String id, String name, String imageUrl, CoordinatesModel coordinatesModel, List<String> categories, LocationModel locationModel, Double rating, String price, String phoneNumber, Integer reviewCount, List<WorkingHoursModel> workingHoursModelDays, Boolean isOpen) {
        super(id, name, imageUrl, coordinatesModel, categories, locationModel);
        this.rating = rating;
        this.price = price;
        this.phoneNumber = phoneNumber;
        this.reviewCount = reviewCount;
        this.workingHoursModelDays = workingHoursModelDays;
        this.isOpen = isOpen;
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
