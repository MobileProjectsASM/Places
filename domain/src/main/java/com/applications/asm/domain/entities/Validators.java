package com.applications.asm.domain.entities;

public class Validators {
    public Boolean validateLatitudeRange(Double latitude) {
        return latitude >= -90 && latitude <= 90;
    }

    public Boolean validateLongitudeRange(Double longitude) {
        return longitude >= -180 && longitude <= 180;
    }

    public Boolean validateRadiusRange(Integer radius) {
        return radius < 0;
    }

    public Boolean validatePage(Integer page) {
        return page < 0;
    }
}
