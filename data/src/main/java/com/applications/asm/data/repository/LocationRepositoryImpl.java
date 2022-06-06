package com.applications.asm.data.repository;

import com.applications.asm.data.sources.LocationDataSourceSP;
import com.applications.asm.domain.entities.Location;
import com.applications.asm.domain.repository.LocationRepository;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class LocationRepositoryImpl implements LocationRepository {
    private final LocationDataSourceSP locationDataSourceSP;

    public LocationRepositoryImpl(LocationDataSourceSP locationDataSourceSP) {
        this.locationDataSourceSP = locationDataSourceSP;
    }

    @Override
    public Completable saveLocation(Double latitude, Double longitude) {
        return locationDataSourceSP.saveLocation(latitude, longitude);
    }

    @Override
    public Single<Location> getLocation() {
        return Single.zip(locationDataSourceSP.getLatitude(), locationDataSourceSP.getLongitude(), Location::new);
    }
}
