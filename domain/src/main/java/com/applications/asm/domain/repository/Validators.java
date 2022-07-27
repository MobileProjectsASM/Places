package com.applications.asm.domain.repository;

public interface Validators {
    Boolean validateLatitudeRange(Double latitude);
    Boolean validateLongitudeRange(Double longitude);
    Boolean validateRadiusRange(Integer radius);
    Boolean validatePage(Integer page);
}
