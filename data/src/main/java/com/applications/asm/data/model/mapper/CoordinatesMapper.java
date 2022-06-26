package com.applications.asm.data.model.mapper;

import com.applications.asm.data.model.CoordinatesModel;
import com.applications.asm.domain.entities.Location;

public interface CoordinatesMapper {
    Location getLocation(CoordinatesModel coordinatesModel);
    CoordinatesModel getCoordinates(Location location);
}
