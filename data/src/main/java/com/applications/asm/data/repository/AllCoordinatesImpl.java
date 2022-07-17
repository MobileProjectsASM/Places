package com.applications.asm.data.repository;

import com.applications.asm.data.framework.local.gps.GpsClient;
import com.applications.asm.data.framework.local.shared_preferences.LocalPersistenceClient;
import com.applications.asm.data.framework.local.shared_preferences.model.CoordinatesSP;
import com.applications.asm.data.mapper.CoordinatesMapper;
import com.applications.asm.domain.entities.Coordinates;
import com.applications.asm.domain.repository.AllCoordinates;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class AllCoordinatesImpl implements AllCoordinates {
    private final LocalPersistenceClient localPersistenceClient;
    private final GpsClient gpsClient;
    private final CoordinatesMapper coordinatesMapper;

    public AllCoordinatesImpl(LocalPersistenceClient localPersistenceClient, GpsClient gpsClient, CoordinatesMapper coordinatesMapper) {
        this.localPersistenceClient = localPersistenceClient;
        this.gpsClient = gpsClient;
        this.coordinatesMapper = coordinatesMapper;
    }

    @Override
    public Single<Coordinates> myLocation(Coordinates.State state) {
        if(state == Coordinates.State.SAVED)
            return localPersistenceClient.getCoordinates().map(coordinatesMapper::coordinatesSpToCoordinates);
        else
            return gpsClient.getCurrentLocation().map(coordinatesMapper::locationToCoordinates);
    }

    @Override
    public Completable saveThis(Coordinates coordinates) {
        return localPersistenceClient.saveCoordinates(new CoordinatesSP(coordinates.getLatitude(), coordinates.getLongitude()));
    }
}
