package com.applications.asm.data.framework;

import android.content.SharedPreferences;

import com.applications.asm.data.sources.LocationDataSourceSP;

public class LocationSharedPreferences implements LocationDataSourceSP {
    private static final String LATITUDE_KEY = "latitude";
    private static final String LONGITUDE_KEY = "longitude";
    private final SharedPreferences appPreferences;

    public LocationSharedPreferences(SharedPreferences appPreferences) {
        this.appPreferences = appPreferences;
    }

    @Override
    public void saveLocation(Double latitude, Double longitude) {
        SharedPreferences.Editor editor = appPreferences.edit();
        editor.putFloat(LATITUDE_KEY, latitude.floatValue());
        editor.putFloat(LONGITUDE_KEY, longitude.floatValue());
        editor.commit();
    }

    @Override
    public double getLatitude() {
        return appPreferences.getFloat(LATITUDE_KEY, 0);
    }

    @Override
    public double getLongitude() {
        return appPreferences.getFloat(LONGITUDE_KEY, 0);
    }
}
