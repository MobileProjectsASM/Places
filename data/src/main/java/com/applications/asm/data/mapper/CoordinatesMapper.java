package com.applications.asm.data.mapper;

import android.location.Location;

import com.applications.asm.data.framework.local.shared_preferences.model.CoordinatesSP;
import com.applications.asm.domain.entities.Coordinates;

public interface CoordinatesMapper {
    Coordinates coordinatesSpToCoordinates(CoordinatesSP coordinatesSP);
    Coordinates locationToCoordinates(Location location);
}
