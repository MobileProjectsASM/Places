package com.applications.asm.data.repository;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ValidatorsImplTest {
    private ValidatorsImpl validators;

    @Before
    public void setUp() {
        validators = new ValidatorsImpl();
    }

    @Test
    public void validatorsNotNullTest() {
        assertNotNull(validators);
    }

    @Test
    public void validateLatitudeIsValid() {
        double latitude = 50.4;
        assertTrue(validators.validateLatitudeRange(latitude));
    }

    @Test
    public void validateLatitudeInvalid() {
        double latitude = 90.1;
        assertFalse(validators.validateLatitudeRange(latitude));
    }

    @Test
    public void validateLongitudeValid() {
        double longitude = 180.0;
        assertTrue(validators.validateLongitudeRange(longitude));
    }

    @Test
    public void validateLongitudeInvalid() {
        double longitude = -180.1;
        assertFalse(validators.validateLongitudeRange(longitude));
    }

    @Test
    public void validateRadiusRangeValid() {
        int radius = 1;
        assertTrue(validators.validateRadiusRange(radius));
    }

    @Test
    public void validateRadiusOutOfRange() {
        int radius = -1;
        assertFalse(validators.validateRadiusRange(radius));
    }

    @Test
    public void pageIsLargerThanZero() {
        assertTrue(validators.isValidPage(1));
    }

    @Test
    public void pageIsLessThanZero() {
        assertFalse(validators.isValidPage(0));
    }
}