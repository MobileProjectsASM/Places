package com.applications.asm.data.entity;

public class ReviewEntity {
    private String userName;
    private String imageUrl;
    private String dateCreated;
    private Integer rate;
    private String description;

    public ReviewEntity() {}

    public ReviewEntity(String userName, String imageUrl, String dateCreated, Integer rate, String description) {
        this.userName = userName;
        this.imageUrl = imageUrl;
        this.dateCreated = dateCreated;
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

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
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
