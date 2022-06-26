package com.applications.asm.data.model.mapper;

import com.applications.asm.data.model.CoordinatesModel;
import com.applications.asm.domain.entities.Location;

public class CoordinatesMapperImpl implements CoordinatesMapper {
    @Override
    public Location getLocation(CoordinatesModel coordinatesModel) {
        return new Location(coordinatesModel.getLatitude(), coordinatesModel.getLongitude());
    }

    @Override
    public CoordinatesModel getCoordinates(Location location) {
        return new CoordinatesModel(location.getLatitude(), location.getLongitude());
    }
}
