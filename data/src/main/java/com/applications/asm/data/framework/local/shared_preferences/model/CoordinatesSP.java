package com.applications.asm.data.framework.local.shared_preferences.model;

public class CoordinatesSP {
    private double latitude;
    private double longitude;

    public CoordinatesSP(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
