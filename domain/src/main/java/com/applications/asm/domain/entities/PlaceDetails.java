package com.applications.asm.domain.entities;

import java.util.List;

public class PlaceDetails extends Place {
    private Double rating;
    private Price price;
    private String phoneNumber;
    private Integer reviewsCounter;
    private List<WorkingHours> workingHoursDays;
    private Boolean isOpen;

    public PlaceDetails(
        String id,
        String name,
        Location location,
        String imageUrl,
        List<String> categories,
        String address,
        Double rating,
        Price price,
        String phoneNumber,
        Integer reviewsCounter,
        List<WorkingHours> workingHoursDays,
        Boolean isOpen
    ) {
        super(id, name, location, imageUrl, categories, address);
        this.rating = rating;
        this.price = price;
        this.phoneNumber = phoneNumber;
        this.reviewsCounter = reviewsCounter;
        this.workingHoursDays = workingHoursDays;
        this.isOpen = isOpen;
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

    public Integer getReviewsCounter() {
        return reviewsCounter;
    }

    public void setReviewsCounter(Integer reviewsCounter) {
        this.reviewsCounter = reviewsCounter;
    }

    public List<WorkingHours> getWorkingHoursDays() {
        return workingHoursDays;
    }

    public void setWorkingHoursDays(List<WorkingHours> workingHoursDays) {
        this.workingHoursDays = workingHoursDays;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }
}
