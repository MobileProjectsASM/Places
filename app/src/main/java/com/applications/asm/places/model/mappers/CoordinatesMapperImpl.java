package com.applications.asm.places.model.mappers;

import com.applications.asm.domain.entities.Coordinates;
import com.applications.asm.places.model.CoordinatesVM;

import javax.inject.Inject;

public class CoordinatesMapperImpl implements CoordinatesMapper {

    @Inject
    public CoordinatesMapperImpl() {

    }

    @Override
    public Coordinates.State getState(CoordinatesVM.StateVM coordinatesState) {
        return coordinatesState == CoordinatesVM.StateVM.CURRENT ? Coordinates.State.CURRENT : Coordinates.State.SAVED;
    }

    @Override
    public CoordinatesVM getCoordinatesVM(Coordinates coordinates) {
        return new CoordinatesVM(coordinates.getLatitude(), coordinates.getLongitude());
    }

    @Override
    public Coordinates getCoordinates(CoordinatesVM coordinatesVM) {
        return new Coordinates(coordinatesVM.getLatitude(), coordinatesVM.getLongitude());
    }
}
