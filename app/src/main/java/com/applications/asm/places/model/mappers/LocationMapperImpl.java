package com.applications.asm.places.model.mappers;

import com.applications.asm.domain.entities.Location;
import com.applications.asm.places.model.LocationVM;

public class LocationMapperImpl implements LocationMapper {
    @Override
    public LocationVM getLocationVM(Location location) {
        return new LocationVM(location.getLatitude(), location.getLongitude());
    }
}
