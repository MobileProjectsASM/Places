package com.applications.asm.data.repository;

import com.applications.asm.data.framework.local.hardware.GpsClient;
import com.applications.asm.data.framework.local.shared_preferences.LocalPersistenceClient;
import com.applications.asm.data.framework.local.shared_preferences.model.CoordinatesSP;
import com.applications.asm.data.mapper.CoordinatesMapper;
import com.applications.asm.data.utils.ErrorUtils;
import com.applications.asm.domain.entities.Coordinates;
import com.applications.asm.domain.entities.Response;
import com.applications.asm.domain.repository.AllCoordinates;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class AllCoordinatesImpl implements AllCoordinates {
    private final LocalPersistenceClient localPersistenceClient;
    private final GpsClient gpsClient;
    private final CoordinatesMapper coordinatesMapper;

    @Inject
    public AllCoordinatesImpl(LocalPersistenceClient localPersistenceClient, GpsClient gpsClient, CoordinatesMapper coordinatesMapper) {
        this.localPersistenceClient = localPersistenceClient;
        this.gpsClient = gpsClient;
        this.coordinatesMapper = coordinatesMapper;
    }

    @Override
    public Single<Response<Coordinates>> myLocation(Coordinates.State state) {
        Single<Response<Coordinates>> coordinatesSingle;
        if(state == Coordinates.State.SAVED)
            coordinatesSingle = localPersistenceClient.getCoordinates().map(coordinatesMapper::coordinatesSpToCoordinates).map(Response::success);
        else
            coordinatesSingle = gpsClient.getCurrentLocation().map(coordinatesMapper::locationToCoordinates).map(Response::success);
        return coordinatesSingle.onErrorResumeNext(throwable -> Single.error(ErrorUtils.resolveError(throwable, getClass())));
    }

    @Override
    public Completable saveThis(Coordinates coordinates) {
        return localPersistenceClient.saveCoordinates(new CoordinatesSP(coordinates.getLatitude(), coordinates.getLongitude()))
                .onErrorResumeNext(throwable -> Completable.error(ErrorUtils.resolveError(throwable, getClass())));
    }
}
