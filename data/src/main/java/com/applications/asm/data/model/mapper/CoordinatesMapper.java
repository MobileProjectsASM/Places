package com.applications.asm.data.model.mapper;

import com.applications.asm.data.model.CoordinatesModel;
import com.applications.asm.domain.entities.Coordinates;

public interface CoordinatesMapper {
    Coordinates getLocation(CoordinatesModel coordinatesModel);
    CoordinatesModel getCoordinates(Coordinates coordinates);
}
