package com.applications.asm.data.sources;

public interface LocationDataSourceSP {
    void saveLocation(Double latitude, Double longitude);
    double getLatitude();
    double getLongitude();
}
