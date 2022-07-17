package com.applications.asm.domain.repository;

import com.applications.asm.domain.entities.Coordinates;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface AllCoordinates {
    Single<Coordinates> myLocation(Coordinates.State state);
    Completable saveThis(Coordinates coordinates);
}
