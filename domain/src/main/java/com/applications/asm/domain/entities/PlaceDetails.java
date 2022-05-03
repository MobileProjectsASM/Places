package com.applications.asm.domain.entities;

import java.util.List;

public class PlaceDetails {
    private String id;
    private String name;
    private Double rating;
    private Double price;
    private String phoneNumber;
    private Integer reviewsNumber;
    private List<WorkingHours> workingHoursDays;
    private Boolean isOpen;

    public PlaceDetails(String id, String name, Double rating, Double price, String phoneNumber, Integer reviewsNumber, List<WorkingHours> workingHoursDays) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.price = price;
        this.phoneNumber = phoneNumber;
        this.reviewsNumber = reviewsNumber;
        this.workingHoursDays = workingHoursDays;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
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

    public List<WorkingHours> getWorkingHours() {
        return workingHoursDays;
    }

    public void setWorkingHours(List<WorkingHours> workingHoursDays) {
        this.workingHoursDays = workingHoursDays;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Date currentDate) {
        Integer currentDay = currentDate.getDay();
        WorkingHours currentWorkingDay = null;
        for(WorkingHours workingHours: workingHoursDays) {
            if(currentDay.equals(workingHours.getDayNumber())){
                currentWorkingDay = workingHours;
                break;
            }
        }
        if(currentWorkingDay != null)
            isOpen = currentHourIsInRange(currentDate.getHour(), currentWorkingDay.getOpenHour(), currentWorkingDay.getCloseHour());
        else isOpen = false;
    }

    private Boolean currentHourIsInRange(Hour currentHour, Hour openHour, Hour closeHour) {
        if(currentHour.getHour() >= openHour.getHour() && currentHour.getHour() <= closeHour.getHour()) {
            if(currentHour.getHour() == openHour.getHour())
                return currentHour.getMinutes() >= openHour.getMinutes();
            else if(currentHour.getHour() == closeHour.getHour())
                return closeHour.getMinutes() <= closeHour.getMinutes();
            else return true;
        } else return false;
    }
}
