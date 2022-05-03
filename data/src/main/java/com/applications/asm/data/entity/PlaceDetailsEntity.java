package com.applications.asm.data.entity;

import java.util.List;

public class PlaceDetailsEntity {
    private String id;
    private String name;
    private Double rating;
    private String price;
    private String phoneNumber;
    private Integer reviewCount;
    private List<WorkingHoursEntity> workingHoursEntityDays;
    private Boolean isOpen;

    public PlaceDetailsEntity() {}

    public PlaceDetailsEntity(String id, String name, Double rating, String price, String phoneNumber, Integer reviewCount, List<WorkingHoursEntity> workingHoursEntityDays, Boolean isOpen) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.price = price;
        this.phoneNumber = phoneNumber;
        this.reviewCount = reviewCount;
        this.workingHoursEntityDays = workingHoursEntityDays;
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

    public List<WorkingHoursEntity> getWorkingHoursEntityDays() {
        return workingHoursEntityDays;
    }

    public void setWorkingHoursEntityDays(List<WorkingHoursEntity> workingHoursEntityDays) {
        this.workingHoursEntityDays = workingHoursEntityDays;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }
}
