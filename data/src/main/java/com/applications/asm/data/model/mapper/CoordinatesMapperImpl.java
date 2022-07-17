package com.applications.asm.data.model.mapper;

import com.applications.asm.data.model.CoordinatesModel;
import com.applications.asm.domain.entities.Coordinates;

public class CoordinatesMapperImpl implements CoordinatesMapper {
    @Override
    public Coordinates getLocation(CoordinatesModel coordinatesModel) {
        return new Coordinates(coordinatesModel.getLatitude(), coordinatesModel.getLongitude());
    }

    @Override
    public CoordinatesModel getCoordinates(Coordinates coordinates) {
        return new CoordinatesModel(coordinates.getLatitude(), coordinates.getLongitude());
    }
}
