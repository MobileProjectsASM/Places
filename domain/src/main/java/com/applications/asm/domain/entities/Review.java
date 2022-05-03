package com.applications.asm.domain.entities;

public class Review {
    private String userName;
    private String imageUrl;
    private String date;
    private Integer rate;
    private String description;

    public Review(String userName, String imageUrl, String date, Integer rate, String description) {
        this.userName = userName;
        this.imageUrl = imageUrl;
        this.date = date;
        this.rate = rate;
        this.description = description;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
