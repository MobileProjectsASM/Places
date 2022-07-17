package com.applications.asm.data.model;

public class LocationModel {
    private String country;
    private String state;
    private String city;
    private String zipCode;
    private String suburb;
    private String address;

    public LocationModel() {

    }

    public LocationModel(String country, String state, String city, String zipCode, String suburb, String address) {
        this.country = country;
        this.state = state;
        this.city = city;
        this.zipCode = zipCode;
        this.suburb = suburb;
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
