package com.applications.asm.domain.entities;

import java.util.List;

public class PlaceDetails extends Place {
    private Double rating;
    private Price price;
    private String phoneNumber;
    private Integer reviewsCounter;
    private List<Schedule> schedule;
    private Boolean isOpen;

    public PlaceDetails(
        String id,
        String name,
        Coordinates coordinates,
        String imageUrl,
        List<Category> categories,
        String address,
        Double rating,
        Price price,
        String phoneNumber,
        Integer reviewsCounter,
        List<Schedule> schedule,
        Boolean isOpen
    ) {
        super(id, name, coordinates, imageUrl, categories, address);
        this.rating = rating;
        this.price = price;
        this.phoneNumber = phoneNumber;
        this.reviewsCounter = reviewsCounter;
        this.schedule = schedule;
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

    public List<Schedule> getWorkingHoursDays() {
        return schedule;
    }

    public void setWorkingHoursDays(List<Schedule> schedule) {
        this.schedule = schedule;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }
}
