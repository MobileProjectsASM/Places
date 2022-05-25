package com.applications.asm.data.repository;

import com.applications.asm.data.sources.LocationDataSourceSP;
import com.applications.asm.domain.entities.Location;
import com.applications.asm.domain.repository.LocationRepository;

public class LocationRepositoryImpl implements LocationRepository {
    private final LocationDataSourceSP locationDataSourceSP;

    public LocationRepositoryImpl(LocationDataSourceSP locationDataSourceSP) {
        this.locationDataSourceSP = locationDataSourceSP;
    }

    @Override
    public Boolean saveLocation(Double latitude, Double longitude) {
        locationDataSourceSP.saveLatitude(latitude);
        locationDataSourceSP.saveLongitude(longitude);
        return true;
    }

    @Override
    public Location getLocation() {
        Double latitude = locationDataSourceSP.getLatitude();
        Double longitude = locationDataSourceSP.getLongitude();
        return new Location(latitude, longitude);
    }
}
