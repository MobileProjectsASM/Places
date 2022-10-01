package com.applications.asm.data.repository;

import com.applications.asm.domain.repository.Validators;

import javax.inject.Inject;

public class ValidatorsImpl implements Validators {

    @Inject
    public ValidatorsImpl() {

    }

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
        return radius >= 0 && radius < 40000;
    }

    @Override
    public Boolean isValidPage(Integer page) {
        return page > 0;
    }
}
