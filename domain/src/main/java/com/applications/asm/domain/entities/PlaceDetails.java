package com.applications.asm.domain.entities;

import java.util.List;

public class PlaceDetails {
    private String id;
    private String name;
    private Double rating;
    private Price price;
    private String phoneNumber;
    private Integer reviewsNumber;
    private List<WorkingHours> workingHoursDays;
    private List<Review> reviews;
    private Boolean isOpen;

    public PlaceDetails(String id, String name, Double rating, Price price, String phoneNumber, Integer reviewsNumber, List<WorkingHours> workingHoursDays, List<Review> reviews, Boolean isOpen) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.price = price;
        this.phoneNumber = phoneNumber;
        this.reviewsNumber = reviewsNumber;
        this.workingHoursDays = workingHoursDays;
        this.reviews = reviews;
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

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getReviewsNumber() {
        return reviewsNumber;
    }

    public void setReviewsNumber(Integer reviewsNumber) {
        this.reviewsNumber = reviewsNumber;
    }

    public List<WorkingHours> getWorkingHoursDays() {
        return workingHoursDays;
    }

    public void setWorkingHoursDays(List<WorkingHours> workingHoursDays) {
        this.workingHoursDays = workingHoursDays;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }
}
