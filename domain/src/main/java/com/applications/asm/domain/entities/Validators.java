package com.applications.asm.domain.entities;

public interface Validators {
    Boolean validateLatitudeRange(Double latitude);
    Boolean validateLongitudeRange(Double longitude);
    Boolean validateRadiusRange(Integer radius);
    Boolean validatePage(Integer page);
}
