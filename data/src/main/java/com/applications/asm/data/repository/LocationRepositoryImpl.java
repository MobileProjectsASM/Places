package com.applications.asm.data.repository;

import android.util.Log;

import com.applications.asm.data.exception.LocationException;
import com.applications.asm.data.model.mapper.CoordinatesMapper;
import com.applications.asm.data.sources.LocationDataSource;
import com.applications.asm.domain.entities.Location;
import com.applications.asm.domain.exception.DeviceException;
import com.applications.asm.domain.repository.LocationRepository;

import io.reactivex.rxjava3.core.Single;

public class LocationRepositoryImpl implements LocationRepository {
    private final LocationDataSource locationDataSource;
    private final CoordinatesMapper coordinatesMapper;

    public LocationRepositoryImpl(LocationDataSource locationDataSource, CoordinatesMapper coordinatesMapper) {
        this.locationDataSource = locationDataSource;
        this.coordinatesMapper = coordinatesMapper;
    }

    @Override
    public Single<Location> getCurrentLocation() {
        return locationDataSource
                .getCurrentLocation()
                .map(coordinatesMapper::getLocation)
                .doOnError(throwable -> {
                    Exception exception = (Exception) throwable;
                    if(exception instanceof LocationException) {
                        Log.e(getClass().getName(), exception.getMessage());
                        throw new DeviceException(exception.getMessage());
                    }
                    Log.e(getClass().getName(), exception.getMessage());
                    throw new DeviceException(exception.getMessage());
                });
    }
}
