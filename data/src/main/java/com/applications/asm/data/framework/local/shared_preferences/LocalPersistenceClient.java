package com.applications.asm.data.framework.local.shared_preferences;

import com.applications.asm.data.framework.local.shared_preferences.model.CoordinatesSP;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface LocalPersistenceClient {
    Completable saveCoordinates(CoordinatesSP coordinatesSP);
    Single<CoordinatesSP> getCoordinates();
}
