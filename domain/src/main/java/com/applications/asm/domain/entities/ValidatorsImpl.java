package com.applications.asm.domain.entities;

public class ValidatorsImpl implements Validators {

    @Override
    public Boolean validateLatitudeRange(Double latitude) {
        return latitude >= -90 && latitude <= 90;
    }

    @Override
    public Boolean validateLongitudeRange(Double longitude) {
        return longitude >= -180 && longitude <= 180;
    }

    @Override
    public Boolean validateRadiusRange(Integer radius) {
        return radius >= 0 && radius < 10000;
    }

    @Override
    public Boolean validatePage(Integer page) {
        return page < 0;
    }
}
