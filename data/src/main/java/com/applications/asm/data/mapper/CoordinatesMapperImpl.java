package com.applications.asm.data.mapper;

import android.location.Location;

import com.applications.asm.data.framework.local.shared_preferences.model.CoordinatesSP;
import com.applications.asm.domain.entities.Coordinates;

public class CoordinatesMapperImpl implements CoordinatesMapper {
    @Override
    public Coordinates coordinatesSpToCoordinates(CoordinatesSP coordinatesSP) {
        return new Coordinates(coordinatesSP.getLatitude(), coordinatesSP.getLongitude());
    }

    @Override
    public Coordinates locationToCoordinates(Location location) {
        return new Coordinates(location.getLatitude(), location.getLongitude());
    }
}
