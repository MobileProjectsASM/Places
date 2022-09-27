package com.applications.asm.places.model.mappers;

import com.applications.asm.domain.entities.Coordinates;
import com.applications.asm.places.model.CoordinatesVM;

public interface CoordinatesMapper {
    Coordinates.State getState(CoordinatesVM.StateVM coordinatesState);
    CoordinatesVM getCoordinatesVM(Coordinates coordinates);
    Coordinates getCoordinates(CoordinatesVM coordinatesVM);
}
