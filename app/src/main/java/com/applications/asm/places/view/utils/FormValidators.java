package com.applications.asm.places.view.utils;

import java.util.regex.Pattern;

public class FormValidators {
    private static final Pattern regexDecimalNumber = Pattern.compile("^-?(0|[1-9][0-9]*)(\\.?[0-9]+)?$");
    private static final Pattern regexNaturalNumber = Pattern.compile("^[0-9]*$");

    public static boolean isDecimalNumber(String decimal) {
        return regexDecimalNumber.matcher(decimal).matches();
    }

    public static boolean isNaturalNumber(String integer) {
        return regexNaturalNumber.matcher(integer).matches();
    }

    public static Boolean isLatitudeInTheRange(Double latitude) {
        return latitude >= -90 && latitude <= 90;
    }

    public static Boolean isLongitudeInTheRange(Double longitude) {
        return longitude >= -180 && longitude <= 180;
    }

    public static Boolean isRadiusInTheRange(Integer radius, Integer maxRadius) {
        return radius >= 0 && radius <= maxRadius;
    }
}
